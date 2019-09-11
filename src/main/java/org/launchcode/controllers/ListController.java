package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 * Displays lists of all values of a given data column: employer, location, skill, and position type.
 * The constructor called ListController is used to populate columnChoices with values.
 * ColumnChoices hashmap provides a collection of all the different list & search options
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
    }

//said to be index in instructions for assignment
    @RequestMapping(value = "")
    public String list(Model model) {

        model.addAttribute("columns", columnChoices);

        return "list";
    }

    /**The controller uses the query parameter passed in as "column" to determin which values to fetch from "JobData"
     * For "all" it will fetch all job data then render the list-jobs.html (view) template.
     * For everything else it only fetches the values for the given column and passes that to the list-column.html.
     */
    @RequestMapping(value = "values")
    public String listColumnValues(Model model, @RequestParam String column) {
        if (column.equals("all")) {
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();
            model.addAttribute("title", "All Jobs");
            model.addAttribute("jobs", jobs);
            return "list-jobs";
        } else {
            ArrayList<String> items = JobData.findAll(column);
            model.addAttribute("title", "All " + columnChoices.get(column) + " Values");
            model.addAttribute("column", column);
            model.addAttribute("items", items);
            return "list-column";
        }

    }

    /** takes two query parameters "column" and "value". Works similarly to the search functionality ("searching"
     * for a certain value w/in a certain column and displaying job match(es).
     * this method only displays jobs matching a specific value in a specific column. It doesn't deal with "all".
     * this method only displays jobs matching a specific value in a specific column. It doesn't deal with "all".
     */
    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model,
            @RequestParam String column, @RequestParam String value) {

        ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(column, value);
        model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        model.addAttribute("jobs", jobs);

        return "list-jobs";
    }
}
