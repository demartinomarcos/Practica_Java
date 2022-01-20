package ar.edu.unq.epers.tactics.service.impl

import ar.edu.unq.epers.tactics.dao.impl.JDBCPartyDAO
import ar.edu.unq.epers.tactics.modelo.Aventurero
import ar.edu.unq.epers.tactics.modelo.Party
import ar.edu.unq.epers.tactics.service.PartyService

class PartyServiceImpl : PartyService {
    private val dao = JDBCPartyDAO()

    override fun crear(party: Party): Party {
        return dao.crear(party)
    }

    override fun actualizar(party: Party):Party {
        dao.actualizar(party)
    }

    override fun recuperar(idDeLaParty: Long): Party {
        return dao.recuperar(idDeLaParty)
    }

    override fun recuperarTodas(): List<Party> {
        return dao.recuperarTodas()
    }

    override fun agregarAventureroAParty(idDeLaParty: Long, aventurero: Aventurero): Aventurero {
        var partyRecuperada = dao.recuperar(idDeLaParty)
        partyRecuperada.contabilizarNuevoAventurero(aventurero)
        dao.actualizar(partyRecuperada)
        return aventurero
    }

    override fun recuperarOrdenadas(orden: Orden, direccion: Direccion, pagina: Int?): PartyPaginadas {
        TODO("Not yet implemented")
    }
}