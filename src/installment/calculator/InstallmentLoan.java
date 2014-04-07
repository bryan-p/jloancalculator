/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package installment.calculator;

import java.util.Date;
import org.apache.poi.ss.formula.functions.FinanceLib;

/**
 *
 * @author bryan
 */
public class InstallmentLoan extends Loan {
    
    private int termLength;
    
    public InstallmentLoan(int principal, int payFreq, int termLength, double apr,
            double periodInt, Date origDate) {
        super (principal, payFreq, apr, periodInt, origDate);
       
        this.termLength = termLength;
    }
    
    @Override
    public void setPeriodInterest() {
        double unit = 0;
        switch (payFrequency) {
            case 7:
                unit = 52;
                break;
            case 14: 
                unit = 26;
                break;
            case 15:
                unit = 24;
                break;
            case 30: 
                unit = 12;
                break; 
        }
        periodInterest = apr / unit; 
    }
    
    public int getTermLength() {return termLength;}
    public void setTermLength(int termLength) {this.termLength = termLength;}

}
