import java.util.ArrayList;
import java.util.List;

/**
 * @author chenkh
 * @time 2020/11/7 10:03
 */
public class TestSorter {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList();
        list.add(5);
        list.add(2);
        list.add(9);
        list.add(-8);
        list.add(12);
        list.sort((a,b)->Integer.compare(a,b));
        System.out.println(list);
    }
}
