package tests;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "informacoesUsuarioTestData.csv")



public class InformacoesUsuarioTest {

    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setUp(){

        navegador = Web.createChrome();

        // Clicar no link que possui o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();

        // Identificando o formulário de Login
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        // Digitar no campo com name "login" que esta dentro do formulário de id "siginbox" o texto "julio0001"
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        // Digitar no campo com name "password" que esta dentro do formulário de id "siginbox" o texto "123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");

        // Clicar no link com o texto "SIGN IN"
        navegador.findElement(By.linkText("SIGN IN")).click();

        // Clicar em um link que tenha a Class "me"
        navegador.findElement(By.className("me")).click();

        // Clicar em um link que tenha o texto "MORE DATA ABOUT YOU"
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();

    }

    @Test
    public void testAdicionarUmaInformacaoAdicionalDoUsuario (@Param(name="tipo")String tipo, @Param(name="contato")String contato,@Param(name="mensagem")String mensagemEsperada) {

        // Clicar no botão através do seu Xpath
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        // Identificar a Popup onde está o formulário de id addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        // Na combo de name "type" escolher a opção "Phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);

        // No campo de name contact digitar "+5511999991111"
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);

        // Clicar no link de Text "SAVE" que está na popup
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        // Na mensagem de id "toast-container" validar que o texto é "
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Your contact has been added!", mensagemEsperada);



    }

    @Test

    public void removerUmContatoDeUmUsuario() {

        // Clicar no elemento pelo seu Xpath //span[text()="+5511955994433"]/following-sibling::a
        navegador.findElement(By.xpath("//span[text()=\"+5511955994433\"]/following-sibling::a")).click();

        // Confirmar a janela Java Script
        navegador.switchTo().alert().accept();

        // Validar que a mensagem aprensetada foi Rest in peace, dear phone!
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!", mensagem);

        String screenshotArquivo = ("D:\\Users\\hfernandes\\test-report\\taskit\\" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png");
        Screenshot.tirar(navegador, screenshotArquivo);

        // Aguardar até 10 segundos para que a janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        // Clicar no link com o texto "Logout"
        navegador.findElement(By.linkText("Logout")).click();

    }

    @After
    public void tearDown() {
        //Fechar o navegador
        navegador.quit();
    }
}
