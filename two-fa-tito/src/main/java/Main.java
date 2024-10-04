import com.two.factor.authentication.TimeRecordApplication;
import com.two.factor.authentication.appl.facade.timerecord.TimeRecordFacade;
import com.two.factor.authentication.appl.facade.timerecord.impl.TimeRecordFacadeImpl;
import com.two.factor.authentication.data.dao.timerecord.TimeRecordDao;
import com.two.factor.authentication.data.dao.timerecord.impl.TimeRecordDaoImpl;

public class Main {
    public static void main(String[] args) {
        TimeRecordApplication timeRecordApp = new TimeRecordApplication();
        timeRecordApp.run();
    }
}


