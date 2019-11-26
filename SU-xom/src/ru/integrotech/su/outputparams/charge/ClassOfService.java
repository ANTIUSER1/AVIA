package ru.integrotech.su.outputparams.charge;


import ru.integrotech.airline.core.airline.ServiceClass;

import java.util.Objects;

class ClassOfService {

    static ClassOfService of(ServiceClass serviceClass) {
        return new ClassOfService(serviceClass.getType().toString());
    }

    static ClassOfService of(ServiceClass.SERVICE_CLASS_TYPE serviceClassType) {
        return new ClassOfService(serviceClassType.toString());
    }

    String classOfServiceCode;

    private ClassOfService(String classOfServiceCode) {
        this.classOfServiceCode = classOfServiceCode;
    }

    private ClassOfService() {
    }

    public String getClassOfServiceCode() {
        return classOfServiceCode;
    }

    public void setClassOfServiceCode(String classOfServiceCode) {
        this.classOfServiceCode = classOfServiceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassOfService that = (ClassOfService) o;
        return Objects.equals(classOfServiceCode, that.classOfServiceCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classOfServiceCode);
    }
}

