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
package com.thoughtworks.go.domain.feed.stage;

import com.thoughtworks.go.domain.StageIdentifier;
import com.thoughtworks.go.domain.StageResult;
import com.thoughtworks.go.domain.feed.Author;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class StageFeedEntryTest {
    @Test
    public void shouldNotAddDuplicateAuthorsCardsToAuthorsList() {
        StageFeedEntry entry = new StageFeedEntry(1, 1, new StageIdentifier(), 1, new Date(), StageResult.Passed);
        entry.addAuthor(new Author("name", "email"));
        entry.addAuthor(new Author("name", "email"));
        entry.addAuthor(new Author("name1", "email1"));
        assertThat(entry.getAuthors().size()).isEqualTo(2);
    }
}
