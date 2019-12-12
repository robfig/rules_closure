// Copyright 2016 The Closure Rules Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * @fileoverview PhantomJS test runner in-browser code. This file polls the
 *     Closure Library every 200ms to see if the tests have completed, and
 *     reports the result to phantomjs_runner.js.
 */

console.log('setInterval');
window.setInterval(function() {
  if (!window['G_testRunner']) {
    console.log('ERROR: G_testRunner not defined. ' +
        'Did you remember to goog.require(\'goog.testing.jsunit\')?');
    window['callPhantom'](false);
    return;
  }
  console.log('polling for success');
  if (window['G_testRunner'].isFinished()) {
    console.log('reporting to phantom');
    console.log(window['G_testRunner'].getReport());
    window['callPhantom'](window['G_testRunner'].isSuccess());
    console.log('reported to phantom');
  }
}, 5000);
