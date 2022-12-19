package models.services.student;


import models.repositories.student.StudentRepository;
import models.view_models.student.StudentCreateRequest;
import models.view_models.student.StudentUpdateRequest;
import models.view_models.student.StudentViewModel;

import java.util.ArrayList;

public class StudentService implements IStudentService{
    private static StudentService instance = null;
    public static StudentService getInstance(){
        if(instance == null)
            instance = new StudentService();
        return instance;
    }

    @Override
    public boolean insert(StudentCreateRequest request) {
        return StudentRepository.getInstance().insert(request);
    }

    @Override
    public boolean update(StudentUpdateRequest request) {
        return StudentRepository.getInstance().update(request);
    }

    @Override
    public boolean delete(String hashKey, String rangeKey) {
        return StudentRepository.getInstance().delete(hashKey, rangeKey);
    }

    @Override
    public StudentViewModel retrieveById(String hashKey, String rangeKey) {
        return StudentRepository.getInstance().retrieveById(hashKey, rangeKey);
    }

    @Override
    public ArrayList<StudentViewModel> retrieveAll() {
        return StudentRepository.getInstance().retrieveAll();
    }

    @Override
    public boolean containStudentClass(String studentClassId) {
        return StudentRepository.getInstance().containStudentClass(studentClassId);
    }
}
