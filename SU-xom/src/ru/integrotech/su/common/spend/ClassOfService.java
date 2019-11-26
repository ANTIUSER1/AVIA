package ru.integrotech.su.common.spend;


import ru.integrotech.airline.core.airline.ServiceClass;

import java.util.Objects;

public class ClassOfService implements Comparable<ClassOfService> {

    public static ClassOfService of(String classOfServiceName) {
        return new ClassOfService(classOfServiceName);
    }

    public static ClassOfService of(ServiceClass serviceClass) {
        return new ClassOfService(serviceClass.getType().toString());
    }

    public static ClassOfService of(ServiceClass.SERVICE_CLASS_TYPE serviceClassType) {
        return new ClassOfService(serviceClassType.toString());
    }

    public static ClassOfService of() {
        return new ClassOfService();
    }

    private String classOfServiceName;

    private ClassOfService(String classOfServiceName) {
        this.classOfServiceName = classOfServiceName;
    }

    private ClassOfService() {
    }

    public String getClassOfServiceName() {
        return classOfServiceName;
    }

    public void setClassOfServiceName(String classOfServiceName) {
        this.classOfServiceName = classOfServiceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassOfService that = (ClassOfService) o;
        return Objects.equals(classOfServiceName, that.classOfServiceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classOfServiceName);
    }

    @Override
    public int compareTo(ClassOfService o) {
        int result;
        if (this.classOfServiceName == null && o.classOfServiceName == null) result = 0;
        else if (this.classOfServiceName == null) result = -1;
        else result = this.classOfServiceName.compareTo(o.classOfServiceName);
        return result;
    }
}

