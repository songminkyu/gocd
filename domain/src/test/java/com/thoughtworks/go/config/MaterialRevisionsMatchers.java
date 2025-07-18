/*
 * Copyright Thoughtworks, Inc.
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
package com.thoughtworks.go.config;

import com.thoughtworks.go.domain.MaterialRevision;
import com.thoughtworks.go.domain.ModificationVisitor;
import com.thoughtworks.go.domain.materials.Material;
import com.thoughtworks.go.domain.materials.Modification;
import com.thoughtworks.go.domain.materials.ModifiedFile;
import com.thoughtworks.go.domain.materials.Revision;
import org.apache.commons.lang3.Strings;
import org.assertj.core.api.ThrowingConsumer;

import static org.assertj.core.api.Assertions.assertThat;

public class MaterialRevisionsMatchers {

    public static class ModifiedBy implements ModificationVisitor {
        private final String user;
        private final String file;
        private String currentName;
        private boolean contains;

        public ModifiedBy(String user, String file) {
            this.user = user;
            this.file = file;
        }

        @Override
        public void visit(MaterialRevision materialRevision) {
        }

        @Override
        public void visit(Material material, Revision revision) {
        }

        @Override
        public void visit(Modification modification) {
            this.currentName = modification.getUserName();
        }

        @Override
        public void visit(ModifiedFile file) {
            if (Strings.CS.equals(file.getFileName(), this.file) && Strings.CS.equals(currentName, this.user)) {
                contains = true;
            }
        }
    }

    public static class ModifiedFileVisitor implements ModificationVisitor {
        private final String file;
        private boolean contains;

        public ModifiedFileVisitor(String file) {
            this.file = file;
        }

        @Override
        public void visit(MaterialRevision materialRevision) {
        }

        @Override
        public void visit(Material material, Revision revision) {
        }

        @Override
        public void visit(Modification modification) {
        }

        @Override
        public void visit(ModifiedFile file) {
            if (Strings.CS.equals(file.getFileName(), this.file)) {
                contains = true;
            }
        }
    }

    public static ThrowingConsumer<MaterialRevision> containsModifiedBy(String filename, String user) {
        return rr -> {
            ModifiedBy modifiedBy = new ModifiedBy(user, filename);
            rr.accept(modifiedBy);
            assertThat(modifiedBy.contains).describedAs("Should contain a file [" + filename + "] modified by user [" + user + "]").isTrue();
        };
    }

    public static ThrowingConsumer<MaterialRevision> containsModifiedFile(String filename) {
        return rr -> {
            ModifiedFileVisitor modifiedBy = new ModifiedFileVisitor(filename);
            rr.accept(modifiedBy);
            assertThat(modifiedBy.contains).describedAs("Should contain a file " + filename).isTrue();
        };
    }
}
