/*
 * Copyright (c) 2017.  TestVagrant Technologies
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.testvagrant.ekam.dashboard.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Step {
    @Builder.Default
    private String status = "skipped";

    @Builder.Default
    private String keyword = "test";

    @Builder.Default
    private String name = "sample test step";

    @Builder.Default
    private String error_message = "";

    @Builder.Default
    private Long duration = 0L;

    @Override
    public String toString() {
        return "{"
                + "\"status\":\"" + status + "\""
                + ", \"keyword\":\"" + keyword + "\""
                + ", \"name\":\"" + name + "\""
                + ", \"error_message\":\"" + error_message + "\""
                + ", \"duration\":\"" + duration + "\""
                + "}";
    }
}
