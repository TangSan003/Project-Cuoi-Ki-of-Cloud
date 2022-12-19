package models.services.subject_group;

import models.repositories.subject_group.SubjectGroupRepository;
import models.view_models.subject_group.SubjectGroupCreateRequest;
import models.view_models.subject_group.SubjectGroupUpdateRequest;
import models.view_models.subject_group.SubjectGroupViewModel;

import java.util.ArrayList;

public class SubjectGroupService implements ISubjectGroupService{
    private static SubjectGroupService instance = null;
    public static SubjectGroupService getInstance(){
        if(instance == null)
            instance = new SubjectGroupService();
        return instance;
    }
    @Override
    public boolean insert(SubjectGroupCreateRequest request) {
        return SubjectGroupRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(SubjectGroupUpdateRequest request) {
        return SubjectGroupRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return SubjectGroupRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public SubjectGroupViewModel retrieveById(String hashKey, String rangeKey) {
        return SubjectGroupRepository.getInstance().retrieveById(hashKey, rangeKey);
    }

    @Override
    public ArrayList<SubjectGroupViewModel> retrieveAll() {
        return SubjectGroupRepository.getInstance().retrieveAll();
    }

    @Override
    public boolean containLecture(String lectureId) {
        return SubjectGroupRepository.getInstance().containLecture(lectureId);
    }

    @Override
    public boolean containSubject(String subjectId) {
        return SubjectGroupRepository.getInstance().containSubject(subjectId);
    }

    @Override
    public ArrayList<SubjectGroupViewModel> retrieveSubjectGroupByLectureId(String lectureId) {
        return SubjectGroupRepository.getInstance().retrieveSubjectGroupByLectureId(lectureId);
    }
}
