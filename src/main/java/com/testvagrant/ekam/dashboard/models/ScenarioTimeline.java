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
 *
 */

package com.testvagrant.ekam.dashboard.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScenarioTimeline {
  @Builder.Default private int interval = 0;

  @Builder.Default private String activity = "";

  @Builder.Default private String screenshotFileName = "";

  @Override
  public String toString() {
    return "{"
        + "\"interval\":\""
        + interval
        + "\""
        + ", \"activity\":\""
        + activity
        + "\""
        + ", \"screenshotFileName\":\""
        + screenshotFileName
        + "\""
        + "}}";
  }
}
