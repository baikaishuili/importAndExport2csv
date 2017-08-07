import com.sun.glass.ui.Size;

import java.util.List;

/**
 * Created by cdliwenke on 2017/8/5.
 */
public interface IExportDataQuery {
    List<Object> queryExportData(Object queryPatam,Integer from, Integer size);
}
