package system;

import AdminVoting.AdminVotingInterface;

import java.util.ArrayList;

/**
 * Created by ander on 4/6/2017.
 */
public class ReportsManager {

    private ArrayList<Report> reports;
    private AdminVotingInterface stub;

    public ReportsManager(){
        this.stub = stub;
        reports = new ArrayList<Report>();
    }


    public String convertReportsToHTMLString(){

       try{
           System.out.println(stub.getReports());
       }
       catch(Exception e){
           e.printStackTrace();
       }

        String res = "";

        for(Report r : reports){
            res += "<tr>";
            res += "<td>" + r.getCampaignName() + "</td>";
            res += "<td>" + r.getWinner() + "</td>";
            res += "<td>" + r.getWinVotes() + "</td>";
            res += "<td>" + r.getTotalVotes() + "</td>";
            res += "</tr>";
        }

        System.out.println(res);

        return res;
    }

}
