package homework;


import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    private final TreeMap<Customer, String> map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> smallestEntry = map.firstEntry();
        Customer customer = smallestEntry.getKey();
        return new AbstractMap.SimpleEntry<>(new Customer(customer.getId(), customer.getName(), customer.getScores()), smallestEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> nextEntry = map.higherEntry(customer);
        if (nextEntry != null) {
            Customer nextCustomer = nextEntry.getKey();
            return new AbstractMap.SimpleEntry<>(new Customer(nextCustomer.getId(), nextCustomer.getName(), nextCustomer.getScores()), nextEntry.getValue());
        } else return null;
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }
}
