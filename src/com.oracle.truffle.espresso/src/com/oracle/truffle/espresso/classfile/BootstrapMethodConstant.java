/*
 * Copyright (c) 2007, 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.oracle.truffle.espresso.classfile;

import com.oracle.truffle.espresso.descriptors.SignatureDescriptor;
import com.oracle.truffle.espresso.impl.ByteString;
import com.oracle.truffle.espresso.impl.ByteString.Name;
import com.oracle.truffle.espresso.impl.ByteString.Signature;

public interface BootstrapMethodConstant extends PoolConstant {

    int getBootstrapMethodAttrIndex();

    ByteString<Name> getName(ConstantPool pool);

    ByteString<Signature> getSignature(ConstantPool pool);

    @Override
    default String toString(ConstantPool pool) {
        return "bsmIndex:" + getBootstrapMethodAttrIndex() + " " + getSignature(pool);
    }

    abstract class Indexes implements BootstrapMethodConstant {

        protected final char bootstrapMethodAttrIndex;
        protected final char nameAndTypeIndex;

        Indexes(int bootstrapMethodAttrIndex, int nameAndTypeIndex) {
            this.bootstrapMethodAttrIndex = PoolConstant.u2(bootstrapMethodAttrIndex);
            this.nameAndTypeIndex = PoolConstant.u2(nameAndTypeIndex);
        }

        @Override
        public final int getBootstrapMethodAttrIndex() {
            return bootstrapMethodAttrIndex;
        }

        @Override
        public final ByteString<Name> getName(ConstantPool pool) {
            return pool.nameAndTypeAt(nameAndTypeIndex).getName(pool);
        }

        @Override
        public final ByteString<Signature> getSignature(ConstantPool pool) {
            return SignatureDescriptor.check(pool.nameAndTypeAt(nameAndTypeIndex).getDescriptor(pool));
        }
    }
}
