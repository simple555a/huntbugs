/*
 * Copyright 2016 HuntBugs contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package one.util.huntbugs.testdata;

import one.util.huntbugs.registry.anno.AssertNoWarning;
import one.util.huntbugs.registry.anno.AssertWarning;

/**
 * @author lan
 *
 */
public class TestSyncOnUpdatedField {
    static Object globalLock = new Object();
    
    Object lock = new Object();
    
    @AssertWarning("SynchronizationOnUpdatedField")
    public void testSimpleSync() {
        synchronized (lock) {
            System.out.println("In lock");
            lock = new Object();
        }
    }
    
    @AssertWarning("SynchronizationOnUpdatedField")
    public void testSimpleStatic() {
        synchronized (globalLock) {
            System.out.println("In lock");
            globalLock = new Object();
        }
    }

    @AssertWarning("SynchronizationOnUpdatedField")
    public void testOtherSync(TestSyncOnUpdatedField tsouf) {
        synchronized (tsouf.lock) {
            System.out.println("In lock");
            tsouf.lock = new Object();
        }
    }
    
    @AssertNoWarning("*")
    public void testOtherSyncOk(TestSyncOnUpdatedField tsouf) {
        synchronized (tsouf.lock) {
            System.out.println("In lock");
            lock = new Object();
        }
    }
    
    @AssertNoWarning("*")
    public void testOtherSyncOk2(TestSyncOnUpdatedField tsouf) {
        synchronized (tsouf.lock) {
            tsouf = this;
            System.out.println("In lock");
            tsouf.lock = new Object();
        }
    }
}
