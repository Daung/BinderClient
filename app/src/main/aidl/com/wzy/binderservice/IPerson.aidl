// IPerson.aidl
package com.wzy.binderservice;
import com.wzy.binderservice.Person;

// Declare any non-default types here with import statements

interface IPerson {
   void addPerson(in Person person);
   List<Person> getList();
}
