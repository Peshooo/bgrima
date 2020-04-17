package com.bgrima.server.controller;

import com.bgrima.server.service.RhymeFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@Controller
public class RhymeController {
  private RhymeFinder rhymeFinder;

  @Autowired
  public RhymeController(RhymeFinder rhymeFinder) {
    this.rhymeFinder = rhymeFinder;
  }

  @RequestMapping("/")
  public ModelAndView rhyme(HttpServletRequest request, HttpServletResponse response) {
    ModelAndView modelAndView = new ModelAndView("index");
    String word = request.getParameter("word");

    if (word == null) {
      modelAndView.addObject("rhymes", new ArrayList<>());
      return modelAndView;
    }

    modelAndView.addObject("wordPlaceholder", word);
    modelAndView.addObject("rhymes", rhymeFinder.getRhymes(word));

    return modelAndView;
  }
}
