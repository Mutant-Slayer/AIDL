// IAdditionInterface.aidl
package com.example.aidl;

// Declare any non-default types here with import statements

interface IAdditionInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(int x,int y);

    int subtract(int x,int y);
}