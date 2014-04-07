/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package installment.calculator;

import java.util.Date;

/**
 *
 * @author bryan
 */
public class PDLoan extends Loan {
    
    private int payDwnPeriod; // renewal count when pay downs should start
    private double payDwnAmt; // amount applied to principal on each pay down
    
    public PDLoan (int principal, int payFreq, double apr, double periodInt, int payDwnPeriod, 
            double payDwnAmt, Date origDate) {
        super (principal, payFreq, apr, periodInt, origDate);
        
        this.payDwnPeriod = payDwnPeriod;
        this.payDwnAmt = payDwnAmt;
    }

    public int getPayDwnPeriod() {
        return payDwnPeriod;
    }
    public void setPayDwnPeriod(int payDwnPeriod) {
        this.payDwnPeriod = payDwnPeriod;
    }
    
    public double getPayDwnAmt() {
        return payDwnAmt;
    }
    public void setPayDwnAmt(double payDwnAmt) {
        this.payDwnAmt = payDwnAmt;
    }

    @Override
    public void setPeriodInterest() {
        
        switch (payFrequency) {
            case 7:
                periodInterest = 15.0;
                break;
            case 14: 
            case 15:
                periodInterest = 30.0;
                break;
            case 30: 
                periodInterest = 60.0;
                break; 
        } 
    }
    
    public void setPeriodInterest(double interest) {
        
        periodInterest = interest;
    }
    
}
