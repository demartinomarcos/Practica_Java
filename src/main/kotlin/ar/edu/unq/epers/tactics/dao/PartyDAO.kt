package ar.edu.unq.epers.tactics.dao

import ar.edu.unq.epers.tactics.modelo.Party

interface PartyDAO {
    fun crear(party: Party): Long
    fun actualizar(party: Party)
    fun recuperar(idDeLaParty: Long): Party
    fun recuperarTodas(): List<Party>
}