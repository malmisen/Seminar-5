package serverSide.externalSystems;

import sharedDataObjects.SaleInfoDTO;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 * A class SaleLog which utilises the singleton pattern.
 * The class is logs all information regarding a purchase.
 */
public class SaleLog {
    private static SaleLog instance;
    private static Lock lock = new ReentrantLock();
    private ArrayList<SaleInfoDTO> saleInfo = new ArrayList<>();

    /**
     * A private Constructor, prevents any other class from instantiating.
     */
    private SaleLog(){

    }

    /**
     * static 'instance' method. Ensures that only one instance of this class is acquired at a time.
     * @return instance, The instance of the SaleLog
     */
    public static SaleLog getInstance(){
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new SaleLog();
                }
            }
        }
        return instance;
    }

    public void addSaleInfoToLog(SaleInfoDTO saleInfo){
        this.saleInfo.add(saleInfo);
        System.out.println("Sale info has been added to the sale log");
    }
}
