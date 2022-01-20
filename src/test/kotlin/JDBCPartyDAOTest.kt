import ar.edu.unq.epers.tactics.dao.PartyDAO
import ar.edu.unq.epers.tactics.dao.impl.JDBCPartyDAO
import ar.edu.unq.epers.tactics.modelo.Party
import helpers.DataService
import helpers.impl.DataServiceImpl
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class JDBCPartyDAOTest {
    private val dao: PartyDAO = JDBCPartyDAO()
    private val dataService: DataService = DataServiceImpl()
    lateinit var partyUno: Party
    lateinit var partyDos: Party
    lateinit var partyTres: Party

    @BeforeEach
    fun crearModelo() {
        dataService.eliminarTodo()

        partyUno = Party("No_Compila")
        partyUno.numeroDeAventureros = 4

        partyDos = Party("Alumnos")
        partyDos.numeroDeAventureros = 8

        partyTres = Party("Profesores")
        partyTres.numeroDeAventureros = 16
    }

    @Test
    fun idUnicoAlCrearLaParty() {
        val idPartyUno = dao.crear(partyUno)
        val idPartyDos = dao.crear(partyDos)

        Assert.assertNotEquals(idPartyUno,idPartyDos)
    }

    @Test
    fun recuperacionDePartyMedianteSuId() {
        val idParty = dao.crear(partyUno)
        val partyRecuperada = dao.recuperar(idParty)

        Assert.assertEquals(partyUno.nombre, partyRecuperada.nombre)
    }

    @Test
    fun actualizacionDePartyMedianteSuId() {
        val idParty = dao.crear(partyTres)
        val partyRecuperada = dao.recuperar(idParty)
        partyRecuperada.numeroDeAventureros = 14
        dao.actualizar(partyRecuperada)

        val partyActualizada = dao.recuperar(idParty)

        Assert.assertEquals(14, partyActualizada.numeroDeAventureros)
    }

    @Test
    fun sePuedenRecuperarTodasLasPartiesOrdenadasAlfabeticamente() {
        dao.crear(partyUno)
        dao.crear(partyDos)
        dao.crear(partyTres)

        val parties = dao.recuperarTodas()

        Assert.assertEquals(3, parties.size)
        Assert.assertEquals(partyDos.nombre, parties.get(0).nombre)
        Assert.assertEquals(partyUno.nombre, parties.get(1).nombre)
        Assert.assertEquals(partyTres.nombre, parties.get(2).nombre)
    }
}