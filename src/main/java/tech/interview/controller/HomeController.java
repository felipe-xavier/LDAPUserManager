package tech.interview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Controller for the Home page.
 *
 */
@Controller
public class HomeController {

    /** Home page Mapping
     *
     * @return The Home page reference.
     */
    @GetMapping("/")
    public String Home() {
        return "home";
    }

}
