package nl.tudelft.oopp.demo.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import nl.tudelft.oopp.demo.entities.Quote;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class QuoteController {
    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return randomly selected {@link Quote}.
     */
    @GetMapping("quote")
    @ResponseBody
    public Quote getRandomQuote() {
        Quote q1 = new Quote(
                1,
                "A clever person solves a problem. A wise person avoids it.",
                "Albert Einstein"
        );

        Quote q2 = new Quote(
                2,
                "The computer was born to solve problems that did not exist before.",
                "Bill Gates"
        );

        Quote q3 = new Quote(
                3,
                "Tell me and I forget.  Teach me and I remember.  Involve me and I learn.",
                "Benjamin Franklin"
        );

        ArrayList<Quote> quotes = new ArrayList<>();
        quotes.add(q1);
        quotes.add(q2);
        quotes.add(q3);

        return quotes.get(new Random().nextInt(quotes.size()));
    }

    @GetMapping(path = "login")
    @ResponseBody
    public String loginVerification(@RequestParam String user, @RequestParam String auth){
        Base64.Encoder encoder = Base64.getEncoder();
        String pass = user +":Banaan";
        String encodedAuth = encoder.encodeToString(pass.getBytes());
        try {
            if (auth.equals(encodedAuth)) {
                return "Login was a success!";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Login failed, please try again!, " + encodedAuth + ", should be: " + auth;
    }
}
