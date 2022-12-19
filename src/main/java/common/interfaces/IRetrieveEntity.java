package common.interfaces;

import java.util.ArrayList;

public interface IRetrieveEntity <ReturnType, HashType, RangeType> {
    ReturnType retrieveById(HashType hashKey, RangeType rangeKey);
    ArrayList<ReturnType> retrieveAll();
}
