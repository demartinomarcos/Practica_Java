import ar.edu.unq.epers.tactics.dao.PartyDAO
import ar.edu.unq.epers.tactics.dao.impl.JDBCPartyDAO
import ar.edu.unq.epers.tactics.modelo.Aventurero
import ar.edu.unq.epers.tactics.modelo.Party
import helpers.DataService
import helpers.impl.DataServiceImpl
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PartyServiceTest {
    private val dao: PartyDAO = JDBCPartyDAO()
    private val dataService: DataService = DataServiceImpl()
    lateinit var party: Party
    lateinit var aventurero: Aventurero

    @BeforeEach
    fun crearModelo() {
        dataService.eliminarTodo()

        party = Party("No_Compila")
        party.numeroDeAventureros = 4

        aventurero = Aventurero(party, 100, "Cubero")
    }

    @Test
    fun contabilizaUnNuevoAventureroEnLaParty() {
        party.contabilizarNuevoAventurero(aventurero)

        Assert.assertEquals(5, party.numeroDeAventureros)
    }
}