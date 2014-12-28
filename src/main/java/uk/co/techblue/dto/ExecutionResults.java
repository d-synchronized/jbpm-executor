package uk.co.techblue.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The Class ExecutionResults.
 */
public class ExecutionResults implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -1738336024526084091L;

    /** The data. */
    private Map<String, Object> data = new HashMap<String, Object>();

    /**
     * Instantiates a new execution results.
     */
    public ExecutionResults() {
    }

    /**
     * Sets the data.
     * 
     * @param data the data
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    /**
     * Gets the data.
     * 
     * @return the data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Gets the data.
     * 
     * @param key the key
     * @return the data
     */
    public Object getData(String key) {
        return data.get(key);
    }

    /**
     * Sets the data.
     * 
     * @param key the key
     * @param value the value
     */
    public void setData(String key, Object value) {
        data.put(key, value);
    }

    /**
     * Key set.
     * 
     * @return the sets the
     */
    public Set<String> keySet() {
        return data.keySet();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ExecutionResults{" + "data=" + data + '}';
    }

}
