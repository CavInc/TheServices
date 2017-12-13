package cav.theservices.utils;

public interface ConstantManager {

    String[] LANG = {"UK","RU","EN"};

    String SELECT_LANG = "SELECT_LANG";
    String SERVICE_ID = "SERVICE_ID";

    int ADMIN_MODE = 1;
    int USER_MODE = 0;

    String URL_REGISTRY = "api/registry";
    String URL_DEMAND = "api/newdemand";
    String URL_SERVICE = "api/newservice";
    String URl_ALLSERVICE = "api/allservice";
    String URL_ALLDEVICE = "api/alldevices";
    String URL_DEMANDS = "api/getdemands";
    String URL_CHANGEDEMAND = "api/changedemand";

    String DEVICE_ID = "DEVICE_ID";

    int SERVICE_NEW = 100;
    int SERVICE_EDIT = 101;

    String ALARM_TYPE = "ALARM_TYPE";
    int ALARM_GETSERVICE = 110;
    int  ALARM_DEMAND = 111;

    String MODE_WORK = "MODE_WORK";
    String SELECTED_ID = "SELECTED_ID";
    int DIRECT_LEFT = 0;
    int DIRECT_RIGTH = 1;

    final int NOTIFY_ID = 1001;

    String SELECTED_DEVICE = "SELECTED_DEVICE";

    int DEMAND_STATUS_OK = 2;
    int DEMAND_STATUS_WORKING = 1;
}