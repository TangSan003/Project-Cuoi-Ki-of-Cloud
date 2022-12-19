package models.services.subject;


import models.repositories.subject.SubjectRepository;
import models.view_models.subject.SubjectCreateRequest;
import models.view_models.subject.SubjectUpdateRequest;
import models.view_models.subject.SubjectViewModel;

import java.util.ArrayList;

public class SubjectService implements ISubjectService{
    private static SubjectService instance = null;
    public static SubjectService getInstance(){
        if(instance == null)
            instance = new SubjectService();
        return instance;
    }

    @Override
    public boolean insert(SubjectCreateRequest request) {
        return SubjectRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(SubjectUpdateRequest request) {
        return SubjectRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return SubjectRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public SubjectViewModel retrieveById(String hashKey, String rangeKey) {
        return SubjectRepository.getInstance().retrieveById(hashKey, rangeKey);
    }

    @Override
    public ArrayList<SubjectViewModel> retrieveAll() {
        return SubjectRepository.getInstance().retrieveAll();
    }
}
