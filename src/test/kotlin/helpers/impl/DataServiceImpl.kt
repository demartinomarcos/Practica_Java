package helpers.impl

import ar.edu.unq.epers.tactics.dao.impl.JDBCConnector
import ar.edu.unq.epers.tactics.dao.impl.JDBCPartyDAO
import ar.edu.unq.epers.tactics.modelo.Party
import helpers.DataService
import java.sql.Connection

class DataServiceImpl : DataService {
    override fun eliminarTodo() {
         JDBCConnector.execute { conn: Connection ->
            val ps =
                conn.prepareStatement("DELETE FROM party")
            ps.execute()
            ps.close()
            null
        }
    }

    override fun crearSetDeDatosIniciales() {
        val dao = JDBCPartyDAO()
        var party = Party("partyTest1")
        var party2 = Party("partyTest2")
        var party3 = Party("partyTest3")

        party.numeroDeAventureros = 2
        party2.numeroDeAventureros = 4
        party3.numeroDeAventureros = 8

        dao.crear(party)
        dao.crear(party2)
        dao.crear(party3)
    }
}