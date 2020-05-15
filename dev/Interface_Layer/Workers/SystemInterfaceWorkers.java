package Interface_Layer.Workers;

import Business_Layer.Controllers.Site_Controller;
import Business_Layer.Service;
import Business_Layer.Workers.Controllers.ShiftController;
import Business_Layer.Workers.Utils.InfoObject;
import Business_Layer.Workers.Controllers.WorkerController;

public class SystemInterfaceWorkers {

    private static SystemInterfaceWorkers systemInterfaceWorkers = null;
    private Service service;


    private SystemInterfaceWorkers() {
        service = Service.getInstance();
    }

    public static SystemInterfaceWorkers getInstance(){
        if(systemInterfaceWorkers == null){
            systemInterfaceWorkers = new SystemInterfaceWorkers();
        }
        return systemInterfaceWorkers;
    }

    public InfoObject printAllManagersAvailableInDates(String date, String shiftType) {
        return service.getWorkerController().printAllManagersAvailableInThisDate(date,shiftType);
    }

    public InfoObject printAllWorkers(){
        return service.getWorkerController().printAllWorker();
    }

    public InfoObject printAllWorkersAvailableInDates(String date, String shiftType) {
        return service.getWorkerController().printAllWorkersAvailableInThisDate(date, shiftType);
    }

    public InfoObject createShift(String shiftType, int selectedManagerSn,String chosenWorkersSn,String date) {
        return service.getShiftController().createShift(shiftType, selectedManagerSn, chosenWorkersSn, date);
    }

    public InfoObject printShift(int shiftSn){
        return service.getShiftController().printShift(shiftSn);
    }

    public InfoObject addWorker(int workerId,String workerName,String workerPhoneNumber,int workerBankAccount,int workerSalary,String dateOfStart,String workerJobTitle,String constrains) {
        return service.getWorkerController().addWorker(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle,constrains);
    }

    public InfoObject addDriver(int workerId,String workerName,String workerPhoneNumber,int workerBankAccount,int workerSalary,String dateOfStart,String workerJobTitle,String constrains,String licenses) {
        return service.getWorkerController().addDriver(workerId,workerName,workerPhoneNumber,workerBankAccount,workerSalary,dateOfStart,workerJobTitle,constrains,licenses);
    }

    public InfoObject printWorkerBySn(int workerSn){
        return service.getWorkerController().printWorkerBySn(workerSn);
    }

    public InfoObject printWorkerConstrainsBySn(int workerSn){
        return service.getWorkerController().printWorkersConstrainsBySn(workerSn);
    }

    public InfoObject setNewSalaryBySn(int workerSn,int newSalary){
        return service.getWorkerController().setNewSalaryBySn(workerSn, newSalary);
    }

    public InfoObject removeWorkerBySn(int workerSn){
        return service.getWorkerController().removeWorkerBySn(workerSn);
    }

    public InfoObject resetWorkerConstrainsBySn(int workerSn){
        return service.getWorkerController().resetWorkerConstrainsBySn(workerSn);
    }

    public InfoObject printAllShifts(){
        return service.getShiftController().printAllShits();
    }

    public InfoObject editWorkerConstrainsBySn(int workerSn, String newConstrains){
        return service.getWorkerController().editWorkerConstrainsBySn(workerSn,newConstrains);
    }

    public InfoObject removeLaterShiftForFiredManagerByManagerSn(int workerSn) {
        return service.getShiftController().removeLaterShiftForFiredManagerByManagerSn(workerSn);
    }

    public boolean printAllStores() {
        if(!(Site_Controller.getInstance().printAllSites())){
            // empty
            System.out.println("No stores yet. add stores first");
            return false;
        }
        return true;
    }

    public boolean addNewStore( String name, String city, String street, String number, String name_of_contact, String phone, String site_area) {
        return Site_Controller.getInstance().addsite("store",name,city,street,number,name_of_contact,phone,site_area);
    }

    public boolean setCurrentStore(int currentStore) {
        if(Site_Controller.getInstance().isStoreExcites(currentStore)) {
            service.getWorkerController().setCurrentStoreSN(currentStore);
            service.getShiftController().setCurrentStoreSN(currentStore);
        }else{
            System.out.println("There is no store with this SN");
            return false;
        }
        return true;
    }
}