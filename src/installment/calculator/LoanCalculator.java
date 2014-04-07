/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package installment.calculator;

import installment.enums.PayFrequency;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.ss.formula.functions.Finance;
import org.apache.poi.ss.formula.functions.FinanceLib;

/**
 *
 * Class to calculate both installment and Payday Loans. Could be further
 * extended to other loan types.
 *
 * @author bryan payne
 *
 */
public class LoanCalculator {

    private DateFormat df;
    private InstallmentLoan installLoan;
    private PDLoan pdLoan;
    private Date curDate;
    public static final int DAYS_IN_YEAR = 365;

    /**
     * Default constructor.
     *
     * Sets default values for both installment and Payday Loans.
     *
     * @throws ParseException
     */
    public LoanCalculator() throws ParseException {
        df = DateFormat.getDateInstance(DateFormat.SHORT);

        String curDateStr = df.format(new Date());
        curDate = df.parse(curDateStr);

        installLoan = new InstallmentLoan(300, PayFrequency.BI_WEEKLY.getLoanPeriod(),
                9, 690.0, 23.014, curDate);

        pdLoan = new PDLoan(300, PayFrequency.BI_WEEKLY.getLoanPeriod(),
                782.0, 30.00, 5, 50.0, curDate);

        // ?? might not need this
        installLoan.setDaysInYear(DAYS_IN_YEAR);
        pdLoan.setDaysInYear(DAYS_IN_YEAR);

    }

    /**
     * Calculates the payments for installment loans.
     *
     * Uses Apache.org POI project to implement excel PMT function
     *
     */
    public void calculateInstallPmt() {

        switch (installLoan.getPayFrequency()) {
            case 7:
            case 14:
            case 15:
                installLoan.setPaymentAmt((FinanceLib.pmt(installLoan.getPeriodInterest() / 100,
                        installLoan.getTermLength() * 2, (double) installLoan.getPrincipal(), 0.0, false)));
                break;
            case 30:
                installLoan.setPaymentAmt((FinanceLib.pmt(installLoan.getPeriodInterest() / 100,
                        installLoan.getTermLength(), (double) installLoan.getPrincipal(), 0.0, false)));
        }

    }

    /**
     * Calculates the interest payments for each payment of an installment loan.
     * Uses Apache.org POI project to implement excel IPMT function
     *
     * @param paymentNum - current payment number on this loan
     * @return - interest only of current payment
     */
    public double calculateInstallIntPmt(int paymentNum) {
        double payment = 0.0;

        switch (installLoan.getPayFrequency()) {
            case 7:
            case 14:
            case 15:
                payment = Finance.ipmt(installLoan.getPeriodInterest() / 100,
                        paymentNum, installLoan.getTermLength() * 2,
                        (double) installLoan.getPrincipal(), 0.0, 0);
                break;
            case 30:
                payment = Finance.ipmt(installLoan.getPeriodInterest() / 100,
                        paymentNum, installLoan.getTermLength(),
                        (double) installLoan.getPrincipal(), 0.0, 0);
        }

        return payment;
    }

    /**
     * Calculates the PDL payment for the current payment number based on
     * renewal paydown period that is set.
     *
     * @param renCount - renewal to start paydown on
     * @param balance - current balance on loan (current principal owed)
     * @return - total payment including any paydown amount due
     */
    private double calculatePdlPayment(int renCount, double balance) {
        double payment = 0.0;
        double principal, payDwnAmt, perInt;
        int payDwnPer;

        principal = pdLoan.getPrincipal();
        payDwnAmt = pdLoan.getPayDwnAmt();
        payDwnPer = pdLoan.getPayDwnPeriod();
        perInt = pdLoan.getPeriodInterest();

        if (renCount < payDwnPer) {
            payment = principal * perInt / 100.0;
        } else {
            payment = balance * perInt / 100.0 + payDwnAmt;
        }

        return payment;
    }

    /**
     * calculates PDL current balance.
     *
     * @param renCount - when the paydown should start
     * @return - new balance on loan (new principal)
     */
    private double calculatePdlBalance(int renCount) {
        double balance = 0.0;
        double principal, payDwnAmt;
        int payDwnPer;

        principal = pdLoan.getPrincipal();
        payDwnAmt = pdLoan.getPayDwnAmt();
        payDwnPer = pdLoan.getPayDwnPeriod();

        if (renCount < payDwnPer) {
            balance = principal;
        } else {
            balance = principal - (renCount - payDwnPer + 1) * payDwnAmt;
        }

        return balance;
    }

    /**
     * Calculates the PDL payoff amounts at each successive renewal number. 
     * 
     * @return ArrayList of payments for each renewal number.
     */
    public ArrayList<Double> calculatePdlPayoffAmt() {
        ArrayList<Double> payOffAmts = new ArrayList<Double>();
        double totalPayments = 0.0;
        double balance = pdLoan.getPrincipal();
        int renCount = 0;

        while (balance > 0) {
            ++renCount;

            totalPayments += calculatePdlPayment(renCount, balance);
            balance = calculatePdlBalance(renCount);
            payOffAmts.add(totalPayments + balance);
        }

        return payOffAmts;
    }
    
    public double proRatePmt(int numDays, Loan loan) {
        double amount = 0.0;
        
        loan.getPeriodInterest();
        
        return amount;
    }

    public double getInstallAPR() {
        return installLoan.getApr();
    }

    public void setInstallAPR(double apr) {
        installLoan.setApr(apr);
        installLoan.setPeriodInterest();
    }

    public Date getCurDate() {
        return curDate;
    }

    public Date getOrigDate() {
        return installLoan.getOrigDate();
    }

    public void setOrigDate(Date d) {
        installLoan.setOrigDate(d);
    }

    /**
     * Returns the payment amount calculated by this.calculateInstallPmt()
     *
     * @return installment payment amount
     */
    public double getInstallPaymentAmt() {
        return installLoan.getPaymentAmt();
    }

    public int getInstallPayFreq() {
        return installLoan.getPayFrequency();
    }

    public int getPdlPayFreq() {
        return pdLoan.getPayFrequency();
    }

    /**
     * Sets both install and PDL Pay Frequencies and recalculates installment
     * periodic interest rate.
     *
     * @param payFreq
     */
    public void setPayFreq(int payFreq) {
        installLoan.setPayFrequency(payFreq);
        pdLoan.setPayFrequency(payFreq);

        installLoan.setPeriodInterest();
    }

    public void setPdlPayDwnAmt(double paydown) {
        pdLoan.setPayDwnAmt(paydown);
    }

    public double getPdlPeriodInt() {
        return pdLoan.getPeriodInterest();
    }

    public void setPdlPeriodInt(double interest) {
        pdLoan.setPeriodInterest(interest);
    }

    public void setPdlPayDwnNum(int renNum) {
        pdLoan.setPayDwnPeriod(renNum);
    }

    /**
     * Returns installment loan principal
     *
     * @return
     */
    public int getInstallPrincipal() {
        return installLoan.getPrincipal();
    }

    /**
     * Sets both installment and PDL principal
     *
     * @param principal
     */
    public void setPrincipal(int principal) {
        installLoan.setPrincipal(principal);
        pdLoan.setPrincipal(principal);
    }

    public InstallmentLoan getInstallLoan() {
        return installLoan;
    }
    
    public Date getInstallFirstDueDate() {
        return installLoan.getDueDate();
    }

    public Date getPdlFirstDueDate() {
        return pdLoan.getDueDate();
    }
    
    public PDLoan getPdlLoan() {
        return pdLoan;
    }

    public void setFirstDueDate(Date d) {
        installLoan.setDueDate(d);
        pdLoan.setDueDate(curDate);
    }

    public Date getSemiMonthFirstDate() {
        return installLoan.getSemiMonthlyFirstDate();
    }

    public void setSemiFirstDate(Date d) {
        installLoan.setSemiMonthlyFirstDate(d);
    }

    public Date getSemiMonthSecDate() {
        return installLoan.getSemiMonthlySecondDate();
    }

    public void setSemiSecDate(Date d) {
        installLoan.setSemiMonthlySecondDate(d);
    }

    public int getTermLength() {
        return installLoan.getTermLength();
    }

    public void setTermLength(int term) {
        installLoan.setTermLength(term);
    }

    /**
     * Get the next paydate (due date) for the current loan.
     *
     * @return next date payment is due
     */
    public Date getNextPaydate(Loan loan) {
        Calendar nextDateCal = Calendar.getInstance();
        Date dueDate = new Date(getInstallFirstDueDate().getTime());

        nextDateCal.setTime(dueDate);

        if (loan.getPayFrequency() == PayFrequency.MONTHLY.getLoanPeriod()) {
            nextDateCal.add(Calendar.DAY_OF_MONTH, findMonthlyPaydate(nextDateCal));

        } else if (loan.getPayFrequency() == PayFrequency.SEMI_MONTHLY.getLoanPeriod()) {
            nextDateCal.setTime(findSemiMonthlyPaydate(nextDateCal));
        } else {
            nextDateCal.add(Calendar.DAY_OF_MONTH, loan.getPayFrequency());
        }

        dueDate.setTime(nextDateCal.getTimeInMillis());
        return dueDate;
    }

    /**
     * Find the number of days to add to the given calendar date for monthly pay
     * frequency
     *
     * @param cal - calendar date of current due date
     * @return number of days to add to the calendar date
     */
    private int findMonthlyPaydate(Calendar cal) {
        int days = 0; // hold last day of the month

        switch (cal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            case 28:
                days = 28;
                break;
            case 29:
                days = 29;
                break;
            case 30:
                days = 30;
                break;
            case 31:
                days = 31;
                break;
        }

        return days;
    }

    /**
     * Return the date for the next Semi Monthly due date.
     *
     * @return date object of the next due date
     */
    private Date findSemiMonthlyPaydate(Calendar curPaydate) {

        if (curPaydate.getTime().equals(getInstallFirstDueDate())) {
            return new Date(curPaydate.getTimeInMillis());

        } else {
            return new Date();
        }
    }
}
