package ar.edu.unq.epers.tactics.dao.impl

import ar.edu.unq.epers.tactics.dao.PartyDAO
import ar.edu.unq.epers.tactics.dao.impl.JDBCConnector.execute
import ar.edu.unq.epers.tactics.modelo.Party
import java.sql.Connection
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement


class JDBCPartyDAO : PartyDAO {
    override fun crear(party: Party): Long {
        var id: Long? = null
        execute { conn: Connection ->
            val ps =
                conn.prepareStatement("INSERT INTO party (nombre, numeroDeAventureros) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)
            ps.setString(1, party.nombre)
            ps.setInt(2, party.numeroDeAventureros)
            ps.executeUpdate()
            if (ps.updateCount != 1) {
                throw RuntimeException("No se inserto la party $party")
            }
            var rs = ps.generatedKeys
            if (rs.next()) {
                id = rs.getLong(1)
            }
            ps.close()
            null
        }
        return id!!
    }

    override fun actualizar(party: Party) {
        execute { conn: Connection ->
            val ps =
                conn.prepareStatement("UPDATE party SET nombre= ?, numeroDeAventureros=? WHERE id=?")
            ps.setString(1, party.nombre)
            ps.setInt(2, party.numeroDeAventureros)
            ps.setLong(3, party.id!!)
            ps.execute()
            if (ps.updateCount != 1) {
                throw RuntimeException("No existe la party $party")
            }
            ps.close()
            null
        }
    }

    override fun recuperar(idDeLaParty: Long): Party {
        return execute { conn: Connection ->
            val ps = conn.prepareStatement("SELECT * FROM party WHERE id = ?")
            ps.setLong(1, idDeLaParty)
            val resultSet = ps.executeQuery()
            var party: Party? = null
            while (resultSet.next()) {
                if (party != null) {
                    throw RuntimeException("Existe mas de una party con el id $idDeLaParty")
                }
                party = crearPartyDesde(resultSet)
            }
            ps.close()
            party!!
        }
    }

    fun crearPartyDesde(resultSet: ResultSet): Party {
        //necesita un result set de partys con: id, nombre y nro de aventureros
        val party = Party(resultSet.getString("nombre"))
        party.numeroDeAventureros = resultSet.getInt("numeroDeAventureros")
        party.id = resultSet.getLong("id")
        return party
    }

    override fun recuperarTodas(): List<Party> {
        var partyList: ArrayList<Party> = ArrayList<Party>()
        execute { conn: Connection ->
            val ps = conn.prepareStatement("SELECT * FROM party ORDER BY nombre")
            val resultSet = ps.executeQuery()
            while (resultSet.next()) {
                var party = crearPartyDesde(resultSet)
                partyList.add(party)
            }
        }
        return partyList
    }

    init {
        val initializeScript = javaClass.classLoader.getResource("createAll.sql").readText()
        execute {
            val ps = it.prepareStatement(initializeScript)
            ps.execute()
            ps.close()
            null
        }
    }
}