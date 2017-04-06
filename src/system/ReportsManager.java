package system;

import AdminVoting.AdminVotingInterface;

import java.util.ArrayList;

/**
 * Created by ander on 4/6/2017.
 */
public class ReportsManager {

    private ArrayList<Report> reports;
    private AdminVotingInterface stub;

    public ReportsManager(AdminVotingInterface stub){
        this.stub = stub;
        reports = new ArrayList<Report>();
    }


    public String convertReportsToHTMLString(){

       try{
           reports = stub.getReports();
           //reports.add(new Report("Test Campaign", "SOME WINNER", 100, 150));
       }
       catch(Exception e){
           e.printStackTrace();
       }

        String res = "<table class='table' id='reports-table'>";
        res += "<thead><tr><th>Campaign Name</th><th>Winner Name</th><th>Winner Votes</th><th>Total Votes</th></tr></thead>";
        res += "<tbody>";

        for(Report r : reports){
            res += "<tr>";
            res += "<td>" + r.getCampaignName() + "</td>";
            res += "<td>" + r.getWinner() + "</td>";
            res += "<td>" + r.getWinVotes() + "</td>";
            res += "<td>" + r.getTotalVotes() + "</td>";
            res += "</tr>";
        }

        res += "</tbody></table>";

        System.out.println(res);

        return res;
    }

}
