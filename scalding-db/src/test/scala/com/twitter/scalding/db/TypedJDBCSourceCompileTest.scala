/*
Copyright 2015 Twitter, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.twitter.scalding.db

import com.twitter.scalding.db.macros._

import org.scalatest.WordSpec

case class ExampleTypedJDBCSource() extends TypedJDBCSource[User](AvailableDatabases(Map(Database("asdf") -> ConnectionSpec(ConnectUrl("how"), UserName("are"), Password("you"), Adapter("mysql"), StringEncoding("UTF8"))))) {
  override val database = Database("asdf")
  override val tableName = TableName("test")
}

class TypedJDBCSourceCompileTest extends WordSpec {
  "TypedJDBCSource" should {
    "Pick up correct column definitions for MySQL Driver" in {
      val expectedCreate = """
        |CREATE TABLE `test` (
        |  `date_id`  INT(11) NOT NULL,
        |  `user_name`  VARCHAR(64) NOT NULL,
        |  `age`  INT(11) NULL,
        |  `gender`  VARCHAR(22) DEFAULT 'male' NOT NULL
        |)
        |""".stripMargin('|')
      assert(ExampleTypedJDBCSource().toSqlCreateString === expectedCreate)
    }
  }
}
