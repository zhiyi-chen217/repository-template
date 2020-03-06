package nl.tudelft.oopp.demo.controllers.test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@ExtendWith(ApplicationExtension.class)
class SignUpControllerTest{
    TextField netId;
    TextField email1;
    TextField email2;
    TextField pass1;
    TextField pass2;

    @BeforeEach
    public void setup(FxRobot fxRobot) {
        netId = (TextField) fxRobot.lookup("#netid").query();
        email1 = (TextField) fxRobot.lookup("#email1").query();
        email2 = (TextField) fxRobot.lookup("#email2").query();
        pass1 = (TextField) fxRobot.lookup("#pass1").query();
        pass2 = (TextField) fxRobot.lookup("#pass2").query();
        netId.setText("abc");
        email1.setText("abc@abc.com");
        email2.setText("abc@abc.com");
        pass1.setText("12345678");
        pass2.setText("12345678");
    }

    @Start
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = getClass().getResource("/signUpScene.fxml");
        loader.setLocation(xmlUrl);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        //stage.toFront();
    }

    @Test
    public void wrongNetId(FxRobot fxRobot) throws IOException, URISyntaxException {
        Button submit = (Button) fxRobot.lookup("#submit").query();
        netId.setText("***");
        fxRobot.clickOn(submit);
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#failtext", LabeledMatchers.hasText("Please insert a valid NetID."));
    }

    @Test
    public void invalidEmail(FxRobot fxRobot) throws IOException, URISyntaxException {
        Button submit = (Button) fxRobot.lookup("#submit").query();
        email1.setText("***");
        fxRobot.clickOn(submit);
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#failtext", LabeledMatchers.hasText("Please enter a valid email address."));
    }

    @Test
    public void differentEmail(FxRobot fxRobot) throws IOException, URISyntaxException {
        Button submit = (Button) fxRobot.lookup("#submit").query();
        email2.setText("ab@abc.com");
        fxRobot.clickOn(submit);
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#failtext", LabeledMatchers.hasText("Please make sure the two email addresses are the same."));
    }

    @Test
    public void invalidPass(FxRobot fxRobot) throws IOException, URISyntaxException {
        Button submit = (Button) fxRobot.lookup("#submit").query();
        pass1.setText("");
        fxRobot.clickOn(submit);
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#failtext", LabeledMatchers.hasText("Please insert a valid password."));
    }

    @Test
    public void differentPass(FxRobot fxRobot) throws IOException, URISyntaxException {
        Button submit = (Button) fxRobot.lookup("#submit").query();
        pass1.setText("123456789");
        fxRobot.clickOn(submit);
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#failtext", LabeledMatchers.hasText("Please make sure the two passwords match."));
    }

    @Test
    public void shortPass(FxRobot fxRobot) throws IOException, URISyntaxException {
        Button submit = (Button) fxRobot.lookup("#submit").query();
        pass1.setText("123456");
        pass2.setText("123456");
        fxRobot.clickOn(submit);
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("#failtext", LabeledMatchers.hasText("Password needs to be 8 characters or longer."));
    }
}