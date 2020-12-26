package com.bgrima.server.controller;

import com.bgrima.server.model.Rhyme;
import com.bgrima.server.service.RhymeFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

@Controller
public class RhymeController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/")
    public ModelAndView rhyme(HttpServletRequest request, HttpServletResponse response) {
        String word = request.getParameter("word");

        return getModelAndView(word);
    }

    private ModelAndView getModelAndView(String word) {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("wordPlaceholder", word);
        modelAndView.addObject("rhymes", getRhymes(word));

        return modelAndView;
    }

    private List<Rhyme> getRhymes(String word) {
        logger.info("Word is now {}", word);

        return word == null
                ? Collections.emptyList()
                : RhymeFinder.getRhymes(word);
    }
}
