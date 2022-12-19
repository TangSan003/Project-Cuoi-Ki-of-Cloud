package common.interfaces;

public interface IModifyEntity <CreateType, UpdateType, HashType, RangeType> {
    boolean insert(CreateType request);

    boolean update(UpdateType request);

    boolean delete(HashType hashKey, RangeType rangeKey);
}
