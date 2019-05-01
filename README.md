# Bsuir-Additional-Api
Expansion of the standard API for BSUIR IIS. Includes, for example, a search for free audiences.

<div id="content">
        <div id="project">
          <div class="pull-left">
            <h1>BSUIR Additional API</h1>
          </div>
          <div class="clearfix"></div>
        </div>
        <div id="header">
          <div id="api-_">
            <h2 id="welcome-to-apidoc">API and SDK Documentation</h2>
              <div class="app-desc">Version: 1.0.0</div>
            <hr>
            <div><p>Expansion of the standard API for BSUIR IIS. Includes, for example, a search for free audiences.</p>
<p>API requst example - SERVER_HOST/api/v1/METHOD_NAME?PARRAM=VALUE</p>
</div>
          </div>
        </div>
        <div id="sections">
                <section id="api-Auditoriums">
                  <h1>Auditoriums</h1>
                    <div id="api-Auditoriums-showAuds">
                      <article id="api-Auditoriums-showAuds-0" data-group="User" data-name="showAuds" data-version="0">
                        <div class="pull-left">
                          <h1>showAuds</h1>
                          <p>Show list of auditoriums</p>
                        </div>
                        <div class="pull-right"></div>
                        <div class="clearfix"></div>
                        <p></p>
                        <p class="marked"></p>
                        <p></p>
                        <br>
                        <pre class="prettyprint language-html prettyprinted" data-type="get"><code><span class="pln">/auditoriums</span></code></pre>
                        <p>
                          </p><h3>Usage and SDK Samples</h3>
                        <p></p>
                        <ul class="nav nav-tabs nav-tabs-examples">
                          <li class="active"><a href="#examples-Auditoriums-showAuds-0-curl">Curl</a></li>
                          <li class=""><a href="#examples-Auditoriums-showAuds-0-java">Java</a></li>
                          <li class=""><a href="#examples-Auditoriums-showAuds-0-android">Android</a></li>
                          <!--<li class=""><a href="#examples-Auditoriums-showAuds-0-groovy">Groovy</a></li>-->
                          <li class=""><a href="#examples-Auditoriums-showAuds-0-objc">Obj-C</a></li>
                          <li class=""><a href="#examples-Auditoriums-showAuds-0-javascript">JavaScript</a></li>
                          <!--<li class=""><a href="#examples-Auditoriums-showAuds-0-angular">Angular</a></li>-->
                          <li class=""><a href="#examples-Auditoriums-showAuds-0-csharp">C#</a></li>
                          <li class=""><a href="#examples-Auditoriums-showAuds-0-php">PHP</a></li>
                          <li class=""><a href="#examples-Auditoriums-showAuds-0-perl">Perl</a></li>
                          <li class=""><a href="#examples-Auditoriums-showAuds-0-python">Python</a></li>
                        </ul>

                        <div class="tab-content">
                          <div class="tab-pane active" id="examples-Auditoriums-showAuds-0-curl">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-bsh"><span class="pln">curl </span><span class="pun">-</span><span class="pln">X GET </span><span class="str">"https://virtserver.swaggerhub.com/N1ghtF1re/BsuirAdditionalApi/1.0.0/auditoriums?name=&amp;building=&amp;floor=&amp;type="</span></code></pre>
                          </div>
                          <div class="tab-pane" id="examples-Auditoriums-showAuds-0-java">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-java"><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">auth</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">model</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">api</span><span class="pun">.</span><span class="typ">AuditoriumsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">import</span><span class="pln"> java</span><span class="pun">.</span><span class="pln">io</span><span class="pun">.</span><span class="typ">File</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> java</span><span class="pun">.</span><span class="pln">util</span><span class="pun">.*;</span><span class="pln">

</span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">AuditoriumsApiExample</span><span class="pln"> </span><span class="pun">{</span><span class="pln">

    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">static</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">(</span><span class="typ">String</span><span class="pun">[]</span><span class="pln"> args</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        
        </span><span class="typ">AuditoriumsApi</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">AuditoriumsApi</span><span class="pun">();</span><span class="pln">
        </span><span class="typ">String</span><span class="pln"> name </span><span class="pun">=</span><span class="pln"> name_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Auditorium name (without building postfix). For example - '203а'</span><span class="pln">
        </span><span class="typ">Integer</span><span class="pln"> building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Building number. For example - 2</span><span class="pln">
        </span><span class="typ">Integer</span><span class="pln"> floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Floor number. For example - 2</span><span class="pln">
        </span><span class="typ">String</span><span class="pln"> type </span><span class="pun">=</span><span class="pln"> type_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE</span><span class="pln">
        </span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            array</span><span class="pun">[</span><span class="typ">Auditorium</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showAuds</span><span class="pun">(</span><span class="pln">name</span><span class="pun">,</span><span class="pln"> building</span><span class="pun">,</span><span class="pln"> floor</span><span class="pun">,</span><span class="pln"> type</span><span class="pun">);</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">out</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
        </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">ApiException</span><span class="pln"> e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">err</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="str">"Exception when calling AuditoriumsApi#showAuds"</span><span class="pun">);</span><span class="pln">
            e</span><span class="pun">.</span><span class="pln">printStackTrace</span><span class="pun">();</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                          </div>

                          <div class="tab-pane" id="examples-Auditoriums-showAuds-0-android">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-java"><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">api</span><span class="pun">.</span><span class="typ">AuditoriumsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">AuditoriumsApiExample</span><span class="pln"> </span><span class="pun">{</span><span class="pln">

    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">static</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">(</span><span class="typ">String</span><span class="pun">[]</span><span class="pln"> args</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        </span><span class="typ">AuditoriumsApi</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">AuditoriumsApi</span><span class="pun">();</span><span class="pln">
        </span><span class="typ">String</span><span class="pln"> name </span><span class="pun">=</span><span class="pln"> name_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Auditorium name (without building postfix). For example - '203а'</span><span class="pln">
        </span><span class="typ">Integer</span><span class="pln"> building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Building number. For example - 2</span><span class="pln">
        </span><span class="typ">Integer</span><span class="pln"> floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Floor number. For example - 2</span><span class="pln">
        </span><span class="typ">String</span><span class="pln"> type </span><span class="pun">=</span><span class="pln"> type_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE</span><span class="pln">
        </span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            array</span><span class="pun">[</span><span class="typ">Auditorium</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showAuds</span><span class="pun">(</span><span class="pln">name</span><span class="pun">,</span><span class="pln"> building</span><span class="pun">,</span><span class="pln"> floor</span><span class="pun">,</span><span class="pln"> type</span><span class="pun">);</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">out</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
        </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">ApiException</span><span class="pln"> e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">err</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="str">"Exception when calling AuditoriumsApi#showAuds"</span><span class="pun">);</span><span class="pln">
            e</span><span class="pun">.</span><span class="pln">printStackTrace</span><span class="pun">();</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                          </div>
  <!--
  <div class="tab-pane" id="examples-Auditoriums-showAuds-0-groovy">
  <pre class="prettyprint language-json prettyprinted" data-type="json"><code>Coming Soon!</code></pre>
  </div> -->
                            <div class="tab-pane" id="examples-Auditoriums-showAuds-0-objc">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-cpp"><span class="typ">String</span><span class="pln"> </span><span class="pun">*</span><span class="pln">name </span><span class="pun">=</span><span class="pln"> name_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Auditorium name (without building postfix). For example - '203а' (optional)</span><span class="pln">
</span><span class="typ">Integer</span><span class="pln"> </span><span class="pun">*</span><span class="pln">building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Building number. For example - 2 (optional)</span><span class="pln">
</span><span class="typ">Integer</span><span class="pln"> </span><span class="pun">*</span><span class="pln">floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Floor number. For example - 2 (optional)</span><span class="pln">
</span><span class="typ">String</span><span class="pln"> </span><span class="pun">*</span><span class="pln">type </span><span class="pun">=</span><span class="pln"> type_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE (optional)</span><span class="pln">

</span><span class="typ">AuditoriumsApi</span><span class="pln"> </span><span class="pun">*</span><span class="pln">apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="pun">[[</span><span class="typ">AuditoriumsApi</span><span class="pln"> alloc</span><span class="pun">]</span><span class="pln"> init</span><span class="pun">];</span><span class="pln">

</span><span class="com">// Show list of auditoriums</span><span class="pln">
</span><span class="pun">[</span><span class="pln">apiInstance showAudsWith</span><span class="pun">:</span><span class="pln">name
    building</span><span class="pun">:</span><span class="pln">building
    floor</span><span class="pun">:</span><span class="pln">floor
    type</span><span class="pun">:</span><span class="pln">type
              completionHandler</span><span class="pun">:</span><span class="pln"> </span><span class="pun">^(</span><span class="pln">array</span><span class="pun">[</span><span class="typ">Auditorium</span><span class="pun">]</span><span class="pln"> output</span><span class="pun">,</span><span class="pln"> </span><span class="typ">NSError</span><span class="pun">*</span><span class="pln"> error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                            </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">output</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                                </span><span class="typ">NSLog</span><span class="pun">(@</span><span class="str">"%@"</span><span class="pun">,</span><span class="pln"> output</span><span class="pun">);</span><span class="pln">
                            </span><span class="pun">}</span><span class="pln">
                            </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                                </span><span class="typ">NSLog</span><span class="pun">(@</span><span class="str">"Error: %@"</span><span class="pun">,</span><span class="pln"> error</span><span class="pun">);</span><span class="pln">
                            </span><span class="pun">}</span><span class="pln">
                        </span><span class="pun">}];</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Auditoriums-showAuds-0-javascript">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-js"><span class="kwd">var</span><span class="pln"> </span><span class="typ">BsuirAdditionalApi</span><span class="pln"> </span><span class="pun">=</span><span class="pln"> require</span><span class="pun">(</span><span class="str">'bsuir_additional_api'</span><span class="pun">);</span><span class="pln">

</span><span class="kwd">var</span><span class="pln"> api </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">BsuirAdditionalApi</span><span class="pun">.</span><span class="typ">AuditoriumsApi</span><span class="pun">()</span><span class="pln">
</span><span class="kwd">var</span><span class="pln"> opts </span><span class="pun">=</span><span class="pln"> </span><span class="pun">{</span><span class="pln"> 
  </span><span class="str">'name'</span><span class="pun">:</span><span class="pln"> name_example</span><span class="pun">,</span><span class="pln"> </span><span class="com">// {{String}} Auditorium name (without building postfix). For example - '203а'</span><span class="pln">
  </span><span class="str">'building'</span><span class="pun">:</span><span class="pln"> </span><span class="lit">56</span><span class="pun">,</span><span class="pln"> </span><span class="com">// {{Integer}} Building number. For example - 2</span><span class="pln">
  </span><span class="str">'floor'</span><span class="pun">:</span><span class="pln"> </span><span class="lit">56</span><span class="pun">,</span><span class="pln"> </span><span class="com">// {{Integer}} Floor number. For example - 2</span><span class="pln">
  </span><span class="str">'type'</span><span class="pun">:</span><span class="pln"> type_example </span><span class="com">// {{String}} Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE</span><span class="pln">
</span><span class="pun">};</span><span class="pln">
</span><span class="kwd">var</span><span class="pln"> callback </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">error</span><span class="pun">,</span><span class="pln"> data</span><span class="pun">,</span><span class="pln"> response</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    console</span><span class="pun">.</span><span class="pln">error</span><span class="pun">(</span><span class="pln">error</span><span class="pun">);</span><span class="pln">
  </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">else</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    console</span><span class="pun">.</span><span class="pln">log</span><span class="pun">(</span><span class="str">'API called successfully. Returned data: '</span><span class="pln"> </span><span class="pun">+</span><span class="pln"> data</span><span class="pun">);</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
</span><span class="pun">};</span><span class="pln">
api</span><span class="pun">.</span><span class="pln">showAuds</span><span class="pun">(</span><span class="pln">opts</span><span class="pun">,</span><span class="pln"> callback</span><span class="pun">);</span></code></pre>
                            </div>

                            <!--<div class="tab-pane" id="examples-Auditoriums-showAuds-0-angular">
              <pre class="prettyprint language-json prettyprinted" data-type="json"><code>Coming Soon!</code></pre>
            </div>-->
                            <div class="tab-pane" id="examples-Auditoriums-showAuds-0-csharp">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-cs"><span class="pln">using </span><span class="typ">System</span><span class="pun">;</span><span class="pln">
using </span><span class="typ">System</span><span class="pun">.</span><span class="typ">Diagnostics</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Api</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Client</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Model</span><span class="pun">;</span><span class="pln">

namespace </span><span class="typ">Example</span><span class="pln">
</span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> showAudsExample
    </span><span class="pun">{</span><span class="pln">
        </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">()</span><span class="pln">
        </span><span class="pun">{</span><span class="pln">

            </span><span class="kwd">var</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">AuditoriumsApi</span><span class="pun">();</span><span class="pln">
            </span><span class="kwd">var</span><span class="pln"> name </span><span class="pun">=</span><span class="pln"> name_example</span><span class="pun">;</span><span class="pln">  </span><span class="com">// String | Auditorium name (without building postfix). For example - '203а' (optional) </span><span class="pln">
            </span><span class="kwd">var</span><span class="pln"> building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln">  </span><span class="com">// Integer | Building number. For example - 2 (optional) </span><span class="pln">
            </span><span class="kwd">var</span><span class="pln"> floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln">  </span><span class="com">// Integer | Floor number. For example - 2 (optional) </span><span class="pln">
            </span><span class="kwd">var</span><span class="pln"> type </span><span class="pun">=</span><span class="pln"> type_example</span><span class="pun">;</span><span class="pln">  </span><span class="com">// String | Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE (optional) </span><span class="pln">

            </span><span class="kwd">try</span><span class="pln">
            </span><span class="pun">{</span><span class="pln">
                </span><span class="com">// Show list of auditoriums</span><span class="pln">
                array</span><span class="pun">[</span><span class="typ">Auditorium</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showAuds</span><span class="pun">(</span><span class="pln">name</span><span class="pun">,</span><span class="pln"> building</span><span class="pun">,</span><span class="pln"> floor</span><span class="pun">,</span><span class="pln"> type</span><span class="pun">);</span><span class="pln">
                </span><span class="typ">Debug</span><span class="pun">.</span><span class="typ">WriteLine</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
            </span><span class="pun">}</span><span class="pln">
            </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">Exception</span><span class="pln"> e</span><span class="pun">)</span><span class="pln">
            </span><span class="pun">{</span><span class="pln">
                </span><span class="typ">Debug</span><span class="pun">.</span><span class="typ">Print</span><span class="pun">(</span><span class="str">"Exception when calling AuditoriumsApi.showAuds: "</span><span class="pln"> </span><span class="pun">+</span><span class="pln"> e</span><span class="pun">.</span><span class="typ">Message</span><span class="pln"> </span><span class="pun">);</span><span class="pln">
            </span><span class="pun">}</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Auditoriums-showAuds-0-php">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-php"><span class="pun">&lt;?</span><span class="pln">php
require_once</span><span class="pun">(</span><span class="pln">__DIR__ </span><span class="pun">.</span><span class="pln"> </span><span class="str">'/vendor/autoload.php'</span><span class="pun">);</span><span class="pln">

$api_instance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">Swagger</span><span class="pln">\Client\ApiAuditoriumsApi</span><span class="pun">();</span><span class="pln">
$name </span><span class="pun">=</span><span class="pln"> name_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Auditorium name (without building postfix). For example - '203а'</span><span class="pln">
$building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Building number. For example - 2</span><span class="pln">
$floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Floor number. For example - 2</span><span class="pln">
$type </span><span class="pun">=</span><span class="pln"> type_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE</span><span class="pln">

</span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    $result </span><span class="pun">=</span><span class="pln"> $api_instance</span><span class="pun">-&gt;</span><span class="pln">showAuds</span><span class="pun">(</span><span class="pln">$name</span><span class="pun">,</span><span class="pln"> $building</span><span class="pun">,</span><span class="pln"> $floor</span><span class="pun">,</span><span class="pln"> $type</span><span class="pun">);</span><span class="pln">
    print_r</span><span class="pun">(</span><span class="pln">$result</span><span class="pun">);</span><span class="pln">
</span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">Exception</span><span class="pln"> $e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    echo </span><span class="str">'Exception when calling AuditoriumsApi-&gt;showAuds: '</span><span class="pun">,</span><span class="pln"> $e</span><span class="pun">-&gt;</span><span class="pln">getMessage</span><span class="pun">(),</span><span class="pln"> PHP_EOL</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span><span class="pln">
</span><span class="pun">?&gt;</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Auditoriums-showAuds-0-perl">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-perl"><span class="kwd">use</span><span class="pln"> </span><span class="typ">Data</span><span class="pun">::</span><span class="typ">Dumper</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">use</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">Configuration</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">use</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">AuditoriumsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">my</span><span class="pln"> $api_instance </span><span class="pun">=</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">AuditoriumsApi</span><span class="pun">-&gt;</span><span class="pln">new</span><span class="pun">();</span><span class="pln">
</span><span class="kwd">my</span><span class="pln"> $name </span><span class="pun">=</span><span class="pln"> name_example</span><span class="pun">;</span><span class="pln"> </span><span class="com"># String | Auditorium name (without building postfix). For example - '203а'</span><span class="pln">
</span><span class="kwd">my</span><span class="pln"> $building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com"># Integer | Building number. For example - 2</span><span class="pln">
</span><span class="kwd">my</span><span class="pln"> $floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com"># Integer | Floor number. For example - 2</span><span class="pln">
</span><span class="kwd">my</span><span class="pln"> $type </span><span class="pun">=</span><span class="pln"> type_example</span><span class="pun">;</span><span class="pln"> </span><span class="com"># String | Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE</span><span class="pln">

</span><span class="kwd">eval</span><span class="pln"> </span><span class="pun">{</span><span class="pln"> 
    </span><span class="kwd">my</span><span class="pln"> $result </span><span class="pun">=</span><span class="pln"> $api_instance</span><span class="pun">-&gt;</span><span class="pln">showAuds</span><span class="pun">(</span><span class="pln">name </span><span class="pun">=&gt;</span><span class="pln"> $name</span><span class="pun">,</span><span class="pln"> building </span><span class="pun">=&gt;</span><span class="pln"> $building</span><span class="pun">,</span><span class="pln"> floor </span><span class="pun">=&gt;</span><span class="pln"> $floor</span><span class="pun">,</span><span class="pln"> type </span><span class="pun">=&gt;</span><span class="pln"> $type</span><span class="pun">);</span><span class="pln">
    </span><span class="kwd">print</span><span class="pln"> </span><span class="typ">Dumper</span><span class="pun">(</span><span class="pln">$result</span><span class="pun">);</span><span class="pln">
</span><span class="pun">};</span><span class="pln">
</span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">$@</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    warn </span><span class="str">"Exception when calling AuditoriumsApi-&gt;showAuds: $@\n"</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Auditoriums-showAuds-0-python">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-python"><span class="kwd">from</span><span class="pln"> __future__ </span><span class="kwd">import</span><span class="pln"> print_statement
</span><span class="kwd">import</span><span class="pln"> time
</span><span class="kwd">import</span><span class="pln"> swagger_client
</span><span class="kwd">from</span><span class="pln"> swagger_client</span><span class="pun">.</span><span class="pln">rest </span><span class="kwd">import</span><span class="pln"> </span><span class="typ">ApiException</span><span class="pln">
</span><span class="kwd">from</span><span class="pln"> pprint </span><span class="kwd">import</span><span class="pln"> pprint

</span><span class="com"># create an instance of the API class</span><span class="pln">
api_instance </span><span class="pun">=</span><span class="pln"> swagger_client</span><span class="pun">.</span><span class="typ">AuditoriumsApi</span><span class="pun">()</span><span class="pln">
name </span><span class="pun">=</span><span class="pln"> name_example </span><span class="com"># String | Auditorium name (without building postfix). For example - '203а' (optional)</span><span class="pln">
building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pln"> </span><span class="com"># Integer | Building number. For example - 2 (optional)</span><span class="pln">
floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pln"> </span><span class="com"># Integer | Floor number. For example - 2 (optional)</span><span class="pln">
type </span><span class="pun">=</span><span class="pln"> type_example </span><span class="com"># String | Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE (optional)</span><span class="pln">

</span><span class="kwd">try</span><span class="pun">:</span><span class="pln"> 
    </span><span class="com"># Show list of auditoriums</span><span class="pln">
    api_response </span><span class="pun">=</span><span class="pln"> api_instance</span><span class="pun">.</span><span class="pln">show_auds</span><span class="pun">(</span><span class="pln">name</span><span class="pun">=</span><span class="pln">name</span><span class="pun">,</span><span class="pln"> building</span><span class="pun">=</span><span class="pln">building</span><span class="pun">,</span><span class="pln"> floor</span><span class="pun">=</span><span class="pln">floor</span><span class="pun">,</span><span class="pln"> type</span><span class="pun">=</span><span class="pln">type</span><span class="pun">)</span><span class="pln">
    pprint</span><span class="pun">(</span><span class="pln">api_response</span><span class="pun">)</span><span class="pln">
</span><span class="kwd">except</span><span class="pln"> </span><span class="typ">ApiException</span><span class="pln"> </span><span class="kwd">as</span><span class="pln"> e</span><span class="pun">:</span><span class="pln">
    </span><span class="kwd">print</span><span class="pun">(</span><span class="str">"Exception when calling AuditoriumsApi-&gt;showAuds: %s\n"</span><span class="pln"> </span><span class="pun">%</span><span class="pln"> e</span><span class="pun">)</span></code></pre>
                            </div>
                          </div>

                          <h2>Parameters</h2>





                            <div class="methodsubtabletitle">Query parameters</div>
                            <table id="methodsubtable">
                              <tbody><tr>
                                <th width="150px">Name</th>
                                <th>Description</th>
                              </tr>
                                <tr><td style="width:150px;">name</td>
                                <td>
                                
                                
                                    <div id="d2e199_showAuds_name">
                                        <div class="json-schema-view">
                                            <div class="primitive">
                                                <span class="type">
                                                    String
                                                </span>
                                
                                                    <div class="inner description">
                                                        Auditorium name (without building postfix). For example - '203а'
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                </tr>
                                <tr><td style="width:150px;">building</td>
                                <td>
                                
                                
                                    <div id="d2e199_showAuds_building">
                                        <div class="json-schema-view">
                                            <div class="primitive">
                                                <span class="type">
                                                    Integer
                                                </span>
                                
                                                    <div class="inner description">
                                                        Building number. For example - 2
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                </tr>
                                <tr><td style="width:150px;">floor</td>
                                <td>
                                
                                
                                    <div id="d2e199_showAuds_floor">
                                        <div class="json-schema-view">
                                            <div class="primitive">
                                                <span class="type">
                                                    Integer
                                                </span>
                                
                                                    <div class="inner description">
                                                        Floor number. For example - 2
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                </tr>
                                <tr><td style="width:150px;">type</td>
                                <td>
                                
                                
                                    <div id="d2e199_showAuds_type">
                                        <div class="json-schema-view">
                                            <div class="primitive">
                                                <span class="type">
                                                    String
                                                </span>
                                
                                                    <div class="inner description">
                                                        Auditorium type. Allowed values - LESSON_LECTURE, LESSON_LAB, LESSON_PRACTICE
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                </tr>
                            </tbody></table>

                          <h2>Responses</h2>
                            <h3> Status: 200 - successful operation </h3>

                            <ul class="nav nav-tabs nav-tabs-examples">
                                <li class="active">
                                  <a data-toggle="tab" href="#responses-showAuds-200-schema">Schema</a>
                                </li>

                            </ul>

                            <div class="tab-content" style="margin-bottom: 10px;">
                                <div class="tab-pane active" id="responses-showAuds-200-schema">
                                  <div id="responses-showAuds-200-schema-200" style="padding: 30px; border-left: 1px solid #eee; border-right: 1px solid #eee; border-bottom: 1px solid #eee;"><div class="json-schema-view"><div class="array">
          <a class="title"><span class="toggle-handle"></span><span class="opening bracket">[</span></a>
          <div class="inner">
          <div class="json-schema-view"><div class="object">
          <a class="title"><span class="toggle-handle"></span> <span class="opening brace">{</span></a>
          <div class="inner">
            
          <div class="property">
          <span class="name">id:</span>
        <div class="json-schema-view"><div class="primitive">
            <span class="type">integer</span>
            <span class="format">(int32)</span>
        </div></div></div><div class="property">
          <span class="name">type:</span>
        <div class="json-schema-view"><div class="primitive">
            <span class="type">string</span>
        <div class="inner enums">
          <b>Enum:</b>
        <span class="inner"><code>LESSON_LECTURE</code>, <code>LESSON_LAB</code>, <code>LESSON_PRACTICE</code></span></div>
        </div></div></div><div class="property">
          <span class="name">floor:</span>
        <div class="json-schema-view"><div class="primitive">
            <span class="type">integer</span>
            <span class="format">(int32)</span>
        </div></div></div><div class="property">
          <span class="name">building:</span>
        <div class="json-schema-view"><div class="primitive">
            <span class="type">integer</span>
            <span class="format">(int32)</span>
        </div></div></div></div>
          <span class="closing brace">}</span>
        </div></div></div>
          <span class="closing bracket">]</span>
        </div></div></div>
                                  <input id="responses-showAuds-200-schema-data" type="hidden" value="{&quot;type&quot;:&quot;array&quot;,&quot;items&quot;:{&quot;$ref&quot;:&quot;#/components/schemas/Auditorium&quot;},&quot;x-content-type&quot;:&quot;application/json&quot;}">
                                </div>
                            </div>

                            <h3> Status: 400 - Invalid parametres </h3>

                            <ul class="nav nav-tabs nav-tabs-examples">
                            </ul>

                            <div class="tab-content" style="margin-bottom: 10px;">
                            </div>

                        </article>
                      </div>
                      <hr>
                    <div id="api-Auditoriums-showFreeAuds">
                      <article id="api-Auditoriums-showFreeAuds-0" data-group="User" data-name="showFreeAuds" data-version="0">
                        <div class="pull-left">
                          <h1>showFreeAuds</h1>
                          <p>Show list of free auditoriums.</p>
                        </div>
                        <div class="pull-right"></div>
                        <div class="clearfix"></div>
                        <p></p>
                        <p class="marked"></p>
                        <p></p>
                        <br>
                        <pre class="prettyprint language-html prettyprinted" data-type="get"><code><span class="pln">/auditoriums/free</span></code></pre>
                        <p>
                          </p><h3>Usage and SDK Samples</h3>
                        <p></p>
                        <ul class="nav nav-tabs nav-tabs-examples">
                          <li class="active"><a href="#examples-Auditoriums-showFreeAuds-0-curl">Curl</a></li>
                          <li class=""><a href="#examples-Auditoriums-showFreeAuds-0-java">Java</a></li>
                          <li class=""><a href="#examples-Auditoriums-showFreeAuds-0-android">Android</a></li>
                          <!--<li class=""><a href="#examples-Auditoriums-showFreeAuds-0-groovy">Groovy</a></li>-->
                          <li class=""><a href="#examples-Auditoriums-showFreeAuds-0-objc">Obj-C</a></li>
                          <li class=""><a href="#examples-Auditoriums-showFreeAuds-0-javascript">JavaScript</a></li>
                          <!--<li class=""><a href="#examples-Auditoriums-showFreeAuds-0-angular">Angular</a></li>-->
                          <li class=""><a href="#examples-Auditoriums-showFreeAuds-0-csharp">C#</a></li>
                          <li class=""><a href="#examples-Auditoriums-showFreeAuds-0-php">PHP</a></li>
                          <li class=""><a href="#examples-Auditoriums-showFreeAuds-0-perl">Perl</a></li>
                          <li class=""><a href="#examples-Auditoriums-showFreeAuds-0-python">Python</a></li>
                        </ul>

                        <div class="tab-content">
                          <div class="tab-pane active" id="examples-Auditoriums-showFreeAuds-0-curl">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-bsh"><span class="pln">curl </span><span class="pun">-</span><span class="pln">X GET </span><span class="str">"https://virtserver.swaggerhub.com/N1ghtF1re/BsuirAdditionalApi/1.0.0/auditoriums/free?building=&amp;floor=&amp;date=&amp;time="</span></code></pre>
                          </div>
                          <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-java">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-java"><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">auth</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">model</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">api</span><span class="pun">.</span><span class="typ">AuditoriumsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">import</span><span class="pln"> java</span><span class="pun">.</span><span class="pln">io</span><span class="pun">.</span><span class="typ">File</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> java</span><span class="pun">.</span><span class="pln">util</span><span class="pun">.*;</span><span class="pln">

</span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">AuditoriumsApiExample</span><span class="pln"> </span><span class="pun">{</span><span class="pln">

    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">static</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">(</span><span class="typ">String</span><span class="pun">[]</span><span class="pln"> args</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        
        </span><span class="typ">AuditoriumsApi</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">AuditoriumsApi</span><span class="pun">();</span><span class="pln">
        </span><span class="typ">Integer</span><span class="pln"> building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Building number. For example - 2</span><span class="pln">
        </span><span class="typ">Integer</span><span class="pln"> floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Floor number. For example - 2</span><span class="pln">
        </span><span class="typ">String</span><span class="pln"> date </span><span class="pun">=</span><span class="pln"> date_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Date in format "dd.MM.yyyy". Default - Today date</span><span class="pln">
        </span><span class="typ">String</span><span class="pln"> time </span><span class="pun">=</span><span class="pln"> time_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now</span><span class="pln">
        </span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            array</span><span class="pun">[</span><span class="typ">Auditorium</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showFreeAuds</span><span class="pun">(</span><span class="pln">building</span><span class="pun">,</span><span class="pln"> floor</span><span class="pun">,</span><span class="pln"> date</span><span class="pun">,</span><span class="pln"> time</span><span class="pun">);</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">out</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
        </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">ApiException</span><span class="pln"> e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">err</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="str">"Exception when calling AuditoriumsApi#showFreeAuds"</span><span class="pun">);</span><span class="pln">
            e</span><span class="pun">.</span><span class="pln">printStackTrace</span><span class="pun">();</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                          </div>

                          <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-android">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-java"><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">api</span><span class="pun">.</span><span class="typ">AuditoriumsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">AuditoriumsApiExample</span><span class="pln"> </span><span class="pun">{</span><span class="pln">

    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">static</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">(</span><span class="typ">String</span><span class="pun">[]</span><span class="pln"> args</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        </span><span class="typ">AuditoriumsApi</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">AuditoriumsApi</span><span class="pun">();</span><span class="pln">
        </span><span class="typ">Integer</span><span class="pln"> building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Building number. For example - 2</span><span class="pln">
        </span><span class="typ">Integer</span><span class="pln"> floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Floor number. For example - 2</span><span class="pln">
        </span><span class="typ">String</span><span class="pln"> date </span><span class="pun">=</span><span class="pln"> date_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Date in format "dd.MM.yyyy". Default - Today date</span><span class="pln">
        </span><span class="typ">String</span><span class="pln"> time </span><span class="pun">=</span><span class="pln"> time_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now</span><span class="pln">
        </span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            array</span><span class="pun">[</span><span class="typ">Auditorium</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showFreeAuds</span><span class="pun">(</span><span class="pln">building</span><span class="pun">,</span><span class="pln"> floor</span><span class="pun">,</span><span class="pln"> date</span><span class="pun">,</span><span class="pln"> time</span><span class="pun">);</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">out</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
        </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">ApiException</span><span class="pln"> e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">err</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="str">"Exception when calling AuditoriumsApi#showFreeAuds"</span><span class="pun">);</span><span class="pln">
            e</span><span class="pun">.</span><span class="pln">printStackTrace</span><span class="pun">();</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                          </div>
  <!--
  <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-groovy">
  <pre class="prettyprint language-json prettyprinted" data-type="json"><code>Coming Soon!</code></pre>
  </div> -->
                            <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-objc">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-cpp"><span class="typ">Integer</span><span class="pln"> </span><span class="pun">*</span><span class="pln">building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Building number. For example - 2 (optional)</span><span class="pln">
</span><span class="typ">Integer</span><span class="pln"> </span><span class="pun">*</span><span class="pln">floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Floor number. For example - 2 (optional)</span><span class="pln">
</span><span class="typ">String</span><span class="pln"> </span><span class="pun">*</span><span class="pln">date </span><span class="pun">=</span><span class="pln"> date_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Date in format "dd.MM.yyyy". Default - Today date (optional)</span><span class="pln">
</span><span class="typ">String</span><span class="pln"> </span><span class="pun">*</span><span class="pln">time </span><span class="pun">=</span><span class="pln"> time_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now (optional)</span><span class="pln">

</span><span class="typ">AuditoriumsApi</span><span class="pln"> </span><span class="pun">*</span><span class="pln">apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="pun">[[</span><span class="typ">AuditoriumsApi</span><span class="pln"> alloc</span><span class="pun">]</span><span class="pln"> init</span><span class="pun">];</span><span class="pln">

</span><span class="com">// Show list of free auditoriums.</span><span class="pln">
</span><span class="pun">[</span><span class="pln">apiInstance showFreeAudsWith</span><span class="pun">:</span><span class="pln">building
    floor</span><span class="pun">:</span><span class="pln">floor
    date</span><span class="pun">:</span><span class="pln">date
    time</span><span class="pun">:</span><span class="pln">time
              completionHandler</span><span class="pun">:</span><span class="pln"> </span><span class="pun">^(</span><span class="pln">array</span><span class="pun">[</span><span class="typ">Auditorium</span><span class="pun">]</span><span class="pln"> output</span><span class="pun">,</span><span class="pln"> </span><span class="typ">NSError</span><span class="pun">*</span><span class="pln"> error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                            </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">output</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                                </span><span class="typ">NSLog</span><span class="pun">(@</span><span class="str">"%@"</span><span class="pun">,</span><span class="pln"> output</span><span class="pun">);</span><span class="pln">
                            </span><span class="pun">}</span><span class="pln">
                            </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                                </span><span class="typ">NSLog</span><span class="pun">(@</span><span class="str">"Error: %@"</span><span class="pun">,</span><span class="pln"> error</span><span class="pun">);</span><span class="pln">
                            </span><span class="pun">}</span><span class="pln">
                        </span><span class="pun">}];</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-javascript">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-js"><span class="kwd">var</span><span class="pln"> </span><span class="typ">BsuirAdditionalApi</span><span class="pln"> </span><span class="pun">=</span><span class="pln"> require</span><span class="pun">(</span><span class="str">'bsuir_additional_api'</span><span class="pun">);</span><span class="pln">

</span><span class="kwd">var</span><span class="pln"> api </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">BsuirAdditionalApi</span><span class="pun">.</span><span class="typ">AuditoriumsApi</span><span class="pun">()</span><span class="pln">
</span><span class="kwd">var</span><span class="pln"> opts </span><span class="pun">=</span><span class="pln"> </span><span class="pun">{</span><span class="pln"> 
  </span><span class="str">'building'</span><span class="pun">:</span><span class="pln"> </span><span class="lit">56</span><span class="pun">,</span><span class="pln"> </span><span class="com">// {{Integer}} Building number. For example - 2</span><span class="pln">
  </span><span class="str">'floor'</span><span class="pun">:</span><span class="pln"> </span><span class="lit">56</span><span class="pun">,</span><span class="pln"> </span><span class="com">// {{Integer}} Floor number. For example - 2</span><span class="pln">
  </span><span class="str">'date'</span><span class="pun">:</span><span class="pln"> date_example</span><span class="pun">,</span><span class="pln"> </span><span class="com">// {{String}} Date in format "dd.MM.yyyy". Default - Today date</span><span class="pln">
  </span><span class="str">'time'</span><span class="pun">:</span><span class="pln"> time_example </span><span class="com">// {{String}} Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now</span><span class="pln">
</span><span class="pun">};</span><span class="pln">
</span><span class="kwd">var</span><span class="pln"> callback </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">error</span><span class="pun">,</span><span class="pln"> data</span><span class="pun">,</span><span class="pln"> response</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    console</span><span class="pun">.</span><span class="pln">error</span><span class="pun">(</span><span class="pln">error</span><span class="pun">);</span><span class="pln">
  </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">else</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    console</span><span class="pun">.</span><span class="pln">log</span><span class="pun">(</span><span class="str">'API called successfully. Returned data: '</span><span class="pln"> </span><span class="pun">+</span><span class="pln"> data</span><span class="pun">);</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
</span><span class="pun">};</span><span class="pln">
api</span><span class="pun">.</span><span class="pln">showFreeAuds</span><span class="pun">(</span><span class="pln">opts</span><span class="pun">,</span><span class="pln"> callback</span><span class="pun">);</span></code></pre>
                            </div>

                            <!--<div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-angular">
              <pre class="prettyprint language-json prettyprinted" data-type="json"><code>Coming Soon!</code></pre>
            </div>-->
                            <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-csharp">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-cs"><span class="pln">using </span><span class="typ">System</span><span class="pun">;</span><span class="pln">
using </span><span class="typ">System</span><span class="pun">.</span><span class="typ">Diagnostics</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Api</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Client</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Model</span><span class="pun">;</span><span class="pln">

namespace </span><span class="typ">Example</span><span class="pln">
</span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> showFreeAudsExample
    </span><span class="pun">{</span><span class="pln">
        </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">()</span><span class="pln">
        </span><span class="pun">{</span><span class="pln">

            </span><span class="kwd">var</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">AuditoriumsApi</span><span class="pun">();</span><span class="pln">
            </span><span class="kwd">var</span><span class="pln"> building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln">  </span><span class="com">// Integer | Building number. For example - 2 (optional) </span><span class="pln">
            </span><span class="kwd">var</span><span class="pln"> floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln">  </span><span class="com">// Integer | Floor number. For example - 2 (optional) </span><span class="pln">
            </span><span class="kwd">var</span><span class="pln"> date </span><span class="pun">=</span><span class="pln"> date_example</span><span class="pun">;</span><span class="pln">  </span><span class="com">// String | Date in format "dd.MM.yyyy". Default - Today date (optional) </span><span class="pln">
            </span><span class="kwd">var</span><span class="pln"> time </span><span class="pun">=</span><span class="pln"> time_example</span><span class="pun">;</span><span class="pln">  </span><span class="com">// String | Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now (optional) </span><span class="pln">

            </span><span class="kwd">try</span><span class="pln">
            </span><span class="pun">{</span><span class="pln">
                </span><span class="com">// Show list of free auditoriums.</span><span class="pln">
                array</span><span class="pun">[</span><span class="typ">Auditorium</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showFreeAuds</span><span class="pun">(</span><span class="pln">building</span><span class="pun">,</span><span class="pln"> floor</span><span class="pun">,</span><span class="pln"> date</span><span class="pun">,</span><span class="pln"> time</span><span class="pun">);</span><span class="pln">
                </span><span class="typ">Debug</span><span class="pun">.</span><span class="typ">WriteLine</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
            </span><span class="pun">}</span><span class="pln">
            </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">Exception</span><span class="pln"> e</span><span class="pun">)</span><span class="pln">
            </span><span class="pun">{</span><span class="pln">
                </span><span class="typ">Debug</span><span class="pun">.</span><span class="typ">Print</span><span class="pun">(</span><span class="str">"Exception when calling AuditoriumsApi.showFreeAuds: "</span><span class="pln"> </span><span class="pun">+</span><span class="pln"> e</span><span class="pun">.</span><span class="typ">Message</span><span class="pln"> </span><span class="pun">);</span><span class="pln">
            </span><span class="pun">}</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-php">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-php"><span class="pun">&lt;?</span><span class="pln">php
require_once</span><span class="pun">(</span><span class="pln">__DIR__ </span><span class="pun">.</span><span class="pln"> </span><span class="str">'/vendor/autoload.php'</span><span class="pun">);</span><span class="pln">

$api_instance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">Swagger</span><span class="pln">\Client\ApiAuditoriumsApi</span><span class="pun">();</span><span class="pln">
$building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Building number. For example - 2</span><span class="pln">
$floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com">// Integer | Floor number. For example - 2</span><span class="pln">
$date </span><span class="pun">=</span><span class="pln"> date_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Date in format "dd.MM.yyyy". Default - Today date</span><span class="pln">
$time </span><span class="pun">=</span><span class="pln"> time_example</span><span class="pun">;</span><span class="pln"> </span><span class="com">// String | Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now</span><span class="pln">

</span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    $result </span><span class="pun">=</span><span class="pln"> $api_instance</span><span class="pun">-&gt;</span><span class="pln">showFreeAuds</span><span class="pun">(</span><span class="pln">$building</span><span class="pun">,</span><span class="pln"> $floor</span><span class="pun">,</span><span class="pln"> $date</span><span class="pun">,</span><span class="pln"> $time</span><span class="pun">);</span><span class="pln">
    print_r</span><span class="pun">(</span><span class="pln">$result</span><span class="pun">);</span><span class="pln">
</span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">Exception</span><span class="pln"> $e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    echo </span><span class="str">'Exception when calling AuditoriumsApi-&gt;showFreeAuds: '</span><span class="pun">,</span><span class="pln"> $e</span><span class="pun">-&gt;</span><span class="pln">getMessage</span><span class="pun">(),</span><span class="pln"> PHP_EOL</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span><span class="pln">
</span><span class="pun">?&gt;</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-perl">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-perl"><span class="kwd">use</span><span class="pln"> </span><span class="typ">Data</span><span class="pun">::</span><span class="typ">Dumper</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">use</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">Configuration</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">use</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">AuditoriumsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">my</span><span class="pln"> $api_instance </span><span class="pun">=</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">AuditoriumsApi</span><span class="pun">-&gt;</span><span class="pln">new</span><span class="pun">();</span><span class="pln">
</span><span class="kwd">my</span><span class="pln"> $building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com"># Integer | Building number. For example - 2</span><span class="pln">
</span><span class="kwd">my</span><span class="pln"> $floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pun">;</span><span class="pln"> </span><span class="com"># Integer | Floor number. For example - 2</span><span class="pln">
</span><span class="kwd">my</span><span class="pln"> $date </span><span class="pun">=</span><span class="pln"> date_example</span><span class="pun">;</span><span class="pln"> </span><span class="com"># String | Date in format "dd.MM.yyyy". Default - Today date</span><span class="pln">
</span><span class="kwd">my</span><span class="pln"> $time </span><span class="pun">=</span><span class="pln"> time_example</span><span class="pun">;</span><span class="pln"> </span><span class="com"># String | Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now</span><span class="pln">

</span><span class="kwd">eval</span><span class="pln"> </span><span class="pun">{</span><span class="pln"> 
    </span><span class="kwd">my</span><span class="pln"> $result </span><span class="pun">=</span><span class="pln"> $api_instance</span><span class="pun">-&gt;</span><span class="pln">showFreeAuds</span><span class="pun">(</span><span class="pln">building </span><span class="pun">=&gt;</span><span class="pln"> $building</span><span class="pun">,</span><span class="pln"> floor </span><span class="pun">=&gt;</span><span class="pln"> $floor</span><span class="pun">,</span><span class="pln"> date </span><span class="pun">=&gt;</span><span class="pln"> $date</span><span class="pun">,</span><span class="pln"> time </span><span class="pun">=&gt;</span><span class="pln"> $time</span><span class="pun">);</span><span class="pln">
    </span><span class="kwd">print</span><span class="pln"> </span><span class="typ">Dumper</span><span class="pun">(</span><span class="pln">$result</span><span class="pun">);</span><span class="pln">
</span><span class="pun">};</span><span class="pln">
</span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">$@</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    warn </span><span class="str">"Exception when calling AuditoriumsApi-&gt;showFreeAuds: $@\n"</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Auditoriums-showFreeAuds-0-python">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-python"><span class="kwd">from</span><span class="pln"> __future__ </span><span class="kwd">import</span><span class="pln"> print_statement
</span><span class="kwd">import</span><span class="pln"> time
</span><span class="kwd">import</span><span class="pln"> swagger_client
</span><span class="kwd">from</span><span class="pln"> swagger_client</span><span class="pun">.</span><span class="pln">rest </span><span class="kwd">import</span><span class="pln"> </span><span class="typ">ApiException</span><span class="pln">
</span><span class="kwd">from</span><span class="pln"> pprint </span><span class="kwd">import</span><span class="pln"> pprint

</span><span class="com"># create an instance of the API class</span><span class="pln">
api_instance </span><span class="pun">=</span><span class="pln"> swagger_client</span><span class="pun">.</span><span class="typ">AuditoriumsApi</span><span class="pun">()</span><span class="pln">
building </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pln"> </span><span class="com"># Integer | Building number. For example - 2 (optional)</span><span class="pln">
floor </span><span class="pun">=</span><span class="pln"> </span><span class="lit">56</span><span class="pln"> </span><span class="com"># Integer | Floor number. For example - 2 (optional)</span><span class="pln">
date </span><span class="pun">=</span><span class="pln"> date_example </span><span class="com"># String | Date in format "dd.MM.yyyy". Default - Today date (optional)</span><span class="pln">
time </span><span class="pun">=</span><span class="pln"> time_example </span><span class="com"># String | Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now (optional)</span><span class="pln">

</span><span class="kwd">try</span><span class="pun">:</span><span class="pln"> 
    </span><span class="com"># Show list of free auditoriums.</span><span class="pln">
    api_response </span><span class="pun">=</span><span class="pln"> api_instance</span><span class="pun">.</span><span class="pln">show_free_auds</span><span class="pun">(</span><span class="pln">building</span><span class="pun">=</span><span class="pln">building</span><span class="pun">,</span><span class="pln"> floor</span><span class="pun">=</span><span class="pln">floor</span><span class="pun">,</span><span class="pln"> date</span><span class="pun">=</span><span class="pln">date</span><span class="pun">,</span><span class="pln"> time</span><span class="pun">=</span><span class="pln">time</span><span class="pun">)</span><span class="pln">
    pprint</span><span class="pun">(</span><span class="pln">api_response</span><span class="pun">)</span><span class="pln">
</span><span class="kwd">except</span><span class="pln"> </span><span class="typ">ApiException</span><span class="pln"> </span><span class="kwd">as</span><span class="pln"> e</span><span class="pun">:</span><span class="pln">
    </span><span class="kwd">print</span><span class="pun">(</span><span class="str">"Exception when calling AuditoriumsApi-&gt;showFreeAuds: %s\n"</span><span class="pln"> </span><span class="pun">%</span><span class="pln"> e</span><span class="pun">)</span></code></pre>
                            </div>
                          </div>

                          <h2>Parameters</h2>





                            <div class="methodsubtabletitle">Query parameters</div>
                            <table id="methodsubtable">
                              <tbody><tr>
                                <th width="150px">Name</th>
                                <th>Description</th>
                              </tr>
                                <tr><td style="width:150px;">building</td>
                                <td>
                                
                                
                                    <div id="d2e199_showFreeAuds_building">
                                        <div class="json-schema-view">
                                            <div class="primitive">
                                                <span class="type">
                                                    Integer
                                                </span>
                                
                                                    <div class="inner description">
                                                        Building number. For example - 2
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                </tr>
                                <tr><td style="width:150px;">floor</td>
                                <td>
                                
                                
                                    <div id="d2e199_showFreeAuds_floor">
                                        <div class="json-schema-view">
                                            <div class="primitive">
                                                <span class="type">
                                                    Integer
                                                </span>
                                
                                                    <div class="inner description">
                                                        Floor number. For example - 2
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                </tr>
                                <tr><td style="width:150px;">date</td>
                                <td>
                                
                                
                                    <div id="d2e199_showFreeAuds_date">
                                        <div class="json-schema-view">
                                            <div class="primitive">
                                                <span class="type">
                                                    String
                                                </span>
                                
                                                    <div class="inner description">
                                                        Date in format "dd.MM.yyyy". Default - Today date
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                </tr>
                                <tr><td style="width:150px;">time</td>
                                <td>
                                
                                
                                    <div id="d2e199_showFreeAuds_time">
                                        <div class="json-schema-view">
                                            <div class="primitive">
                                                <span class="type">
                                                    String
                                                </span>
                                
                                                    <div class="inner description">
                                                        Lesson's time in format "HH:mm" (Any time between start and end of lesson). Default - now
                                                    </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                </tr>
                            </tbody></table>

                          <h2>Responses</h2>
                            <h3> Status: 200 - successful operation </h3>

                            <ul class="nav nav-tabs nav-tabs-examples">
                                <li class="active">
                                  <a data-toggle="tab" href="#responses-showFreeAuds-200-schema">Schema</a>
                                </li>

                            </ul>

                            <div class="tab-content" style="margin-bottom: 10px;">
                                <div class="tab-pane active" id="responses-showFreeAuds-200-schema">
                                  <div id="responses-showFreeAuds-200-schema-200" style="padding: 30px; border-left: 1px solid #eee; border-right: 1px solid #eee; border-bottom: 1px solid #eee;"><div class="json-schema-view"><div class="array">
          <a class="title"><span class="toggle-handle"></span><span class="opening bracket">[</span></a>
          <div class="inner">
          <div class="json-schema-view"><div class="primitive">
            <span class="type">undefined</span>
        </div></div></div>
          <span class="closing bracket">]</span>
        </div></div></div>
                                  <input id="responses-showFreeAuds-200-schema-data" type="hidden" value="{&quot;type&quot;:&quot;array&quot;,&quot;items&quot;:{&quot;$ref&quot;:&quot;#/components/schemas/Auditorium&quot;},&quot;x-content-type&quot;:&quot;application/json&quot;}">
                                </div>
                            </div>

                            <h3> Status: 400 - Invalid parametres </h3>

                            <ul class="nav nav-tabs nav-tabs-examples">
                            </ul>

                            <div class="tab-content" style="margin-bottom: 10px;">
                            </div>

                        </article>
                      </div>
                      <hr>
                  </section>
                <section id="api-Buildings">
                  <h1>Buildings</h1>
                    <div id="api-Buildings-showBuildings">
                      <article id="api-Buildings-showBuildings-0" data-group="User" data-name="showBuildings" data-version="0">
                        <div class="pull-left">
                          <h1>showBuildings</h1>
                          <p>Show a list of buildings with available floors</p>
                        </div>
                        <div class="pull-right"></div>
                        <div class="clearfix"></div>
                        <p></p>
                        <p class="marked"></p>
                        <p></p>
                        <br>
                        <pre class="prettyprint language-html prettyprinted" data-type="get"><code><span class="pln">/buildings</span></code></pre>
                        <p>
                          </p><h3>Usage and SDK Samples</h3>
                        <p></p>
                        <ul class="nav nav-tabs nav-tabs-examples">
                          <li class="active"><a href="#examples-Buildings-showBuildings-0-curl">Curl</a></li>
                          <li class=""><a href="#examples-Buildings-showBuildings-0-java">Java</a></li>
                          <li class=""><a href="#examples-Buildings-showBuildings-0-android">Android</a></li>
                          <!--<li class=""><a href="#examples-Buildings-showBuildings-0-groovy">Groovy</a></li>-->
                          <li class=""><a href="#examples-Buildings-showBuildings-0-objc">Obj-C</a></li>
                          <li class=""><a href="#examples-Buildings-showBuildings-0-javascript">JavaScript</a></li>
                          <!--<li class=""><a href="#examples-Buildings-showBuildings-0-angular">Angular</a></li>-->
                          <li class=""><a href="#examples-Buildings-showBuildings-0-csharp">C#</a></li>
                          <li class=""><a href="#examples-Buildings-showBuildings-0-php">PHP</a></li>
                          <li class=""><a href="#examples-Buildings-showBuildings-0-perl">Perl</a></li>
                          <li class=""><a href="#examples-Buildings-showBuildings-0-python">Python</a></li>
                        </ul>

                        <div class="tab-content">
                          <div class="tab-pane active" id="examples-Buildings-showBuildings-0-curl">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-bsh"><span class="pln">curl </span><span class="pun">-</span><span class="pln">X GET </span><span class="str">"https://virtserver.swaggerhub.com/N1ghtF1re/BsuirAdditionalApi/1.0.0/buildings"</span></code></pre>
                          </div>
                          <div class="tab-pane" id="examples-Buildings-showBuildings-0-java">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-java"><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">auth</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">model</span><span class="pun">.*;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">api</span><span class="pun">.</span><span class="typ">BuildingsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">import</span><span class="pln"> java</span><span class="pun">.</span><span class="pln">io</span><span class="pun">.</span><span class="typ">File</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">import</span><span class="pln"> java</span><span class="pun">.</span><span class="pln">util</span><span class="pun">.*;</span><span class="pln">

</span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">BuildingsApiExample</span><span class="pln"> </span><span class="pun">{</span><span class="pln">

    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">static</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">(</span><span class="typ">String</span><span class="pun">[]</span><span class="pln"> args</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        
        </span><span class="typ">BuildingsApi</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">BuildingsApi</span><span class="pun">();</span><span class="pln">
        </span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            array</span><span class="pun">[</span><span class="typ">Buildings</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showBuildings</span><span class="pun">();</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">out</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
        </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">ApiException</span><span class="pln"> e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">err</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="str">"Exception when calling BuildingsApi#showBuildings"</span><span class="pun">);</span><span class="pln">
            e</span><span class="pun">.</span><span class="pln">printStackTrace</span><span class="pun">();</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                          </div>

                          <div class="tab-pane" id="examples-Buildings-showBuildings-0-android">
                            <pre class="prettyprint prettyprinted" style=""><code class="language-java"><span class="kwd">import</span><span class="pln"> io</span><span class="pun">.</span><span class="pln">swagger</span><span class="pun">.</span><span class="pln">client</span><span class="pun">.</span><span class="pln">api</span><span class="pun">.</span><span class="typ">BuildingsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">BuildingsApiExample</span><span class="pln"> </span><span class="pun">{</span><span class="pln">

    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">static</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">(</span><span class="typ">String</span><span class="pun">[]</span><span class="pln"> args</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
        </span><span class="typ">BuildingsApi</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">BuildingsApi</span><span class="pun">();</span><span class="pln">
        </span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            array</span><span class="pun">[</span><span class="typ">Buildings</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showBuildings</span><span class="pun">();</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">out</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
        </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">ApiException</span><span class="pln"> e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
            </span><span class="typ">System</span><span class="pun">.</span><span class="pln">err</span><span class="pun">.</span><span class="pln">println</span><span class="pun">(</span><span class="str">"Exception when calling BuildingsApi#showBuildings"</span><span class="pun">);</span><span class="pln">
            e</span><span class="pun">.</span><span class="pln">printStackTrace</span><span class="pun">();</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                          </div>
  <!--
  <div class="tab-pane" id="examples-Buildings-showBuildings-0-groovy">
  <pre class="prettyprint language-json prettyprinted" data-type="json"><code>Coming Soon!</code></pre>
  </div> -->
                            <div class="tab-pane" id="examples-Buildings-showBuildings-0-objc">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-cpp"><span class="pln">
</span><span class="typ">BuildingsApi</span><span class="pln"> </span><span class="pun">*</span><span class="pln">apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="pun">[[</span><span class="typ">BuildingsApi</span><span class="pln"> alloc</span><span class="pun">]</span><span class="pln"> init</span><span class="pun">];</span><span class="pln">

</span><span class="com">// Show a list of buildings with available floors</span><span class="pln">
</span><span class="pun">[</span><span class="pln">apiInstance showBuildingsWithCompletionHandler</span><span class="pun">:</span><span class="pln"> 
              </span><span class="pun">^(</span><span class="pln">array</span><span class="pun">[</span><span class="typ">Buildings</span><span class="pun">]</span><span class="pln"> output</span><span class="pun">,</span><span class="pln"> </span><span class="typ">NSError</span><span class="pun">*</span><span class="pln"> error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                            </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">output</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                                </span><span class="typ">NSLog</span><span class="pun">(@</span><span class="str">"%@"</span><span class="pun">,</span><span class="pln"> output</span><span class="pun">);</span><span class="pln">
                            </span><span class="pun">}</span><span class="pln">
                            </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
                                </span><span class="typ">NSLog</span><span class="pun">(@</span><span class="str">"Error: %@"</span><span class="pun">,</span><span class="pln"> error</span><span class="pun">);</span><span class="pln">
                            </span><span class="pun">}</span><span class="pln">
                        </span><span class="pun">}];</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Buildings-showBuildings-0-javascript">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-js"><span class="kwd">var</span><span class="pln"> </span><span class="typ">BsuirAdditionalApi</span><span class="pln"> </span><span class="pun">=</span><span class="pln"> require</span><span class="pun">(</span><span class="str">'bsuir_additional_api'</span><span class="pun">);</span><span class="pln">

</span><span class="kwd">var</span><span class="pln"> api </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">BsuirAdditionalApi</span><span class="pun">.</span><span class="typ">BuildingsApi</span><span class="pun">()</span><span class="pln">
</span><span class="kwd">var</span><span class="pln"> callback </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">function</span><span class="pun">(</span><span class="pln">error</span><span class="pun">,</span><span class="pln"> data</span><span class="pun">,</span><span class="pln"> response</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
  </span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">error</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    console</span><span class="pun">.</span><span class="pln">error</span><span class="pun">(</span><span class="pln">error</span><span class="pun">);</span><span class="pln">
  </span><span class="pun">}</span><span class="pln"> </span><span class="kwd">else</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    console</span><span class="pun">.</span><span class="pln">log</span><span class="pun">(</span><span class="str">'API called successfully. Returned data: '</span><span class="pln"> </span><span class="pun">+</span><span class="pln"> data</span><span class="pun">);</span><span class="pln">
  </span><span class="pun">}</span><span class="pln">
</span><span class="pun">};</span><span class="pln">
api</span><span class="pun">.</span><span class="pln">showBuildings</span><span class="pun">(</span><span class="pln">callback</span><span class="pun">);</span></code></pre>
                            </div>

                            <!--<div class="tab-pane" id="examples-Buildings-showBuildings-0-angular">
              <pre class="prettyprint language-json prettyprinted" data-type="json"><code>Coming Soon!</code></pre>
            </div>-->
                            <div class="tab-pane" id="examples-Buildings-showBuildings-0-csharp">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-cs"><span class="pln">using </span><span class="typ">System</span><span class="pun">;</span><span class="pln">
using </span><span class="typ">System</span><span class="pun">.</span><span class="typ">Diagnostics</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Api</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Client</span><span class="pun">;</span><span class="pln">
using IO</span><span class="pun">.</span><span class="typ">Swagger</span><span class="pun">.</span><span class="typ">Model</span><span class="pun">;</span><span class="pln">

namespace </span><span class="typ">Example</span><span class="pln">
</span><span class="pun">{</span><span class="pln">
    </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> showBuildingsExample
    </span><span class="pun">{</span><span class="pln">
        </span><span class="kwd">public</span><span class="pln"> </span><span class="kwd">void</span><span class="pln"> main</span><span class="pun">()</span><span class="pln">
        </span><span class="pun">{</span><span class="pln">

            </span><span class="kwd">var</span><span class="pln"> apiInstance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">BuildingsApi</span><span class="pun">();</span><span class="pln">

            </span><span class="kwd">try</span><span class="pln">
            </span><span class="pun">{</span><span class="pln">
                </span><span class="com">// Show a list of buildings with available floors</span><span class="pln">
                array</span><span class="pun">[</span><span class="typ">Buildings</span><span class="pun">]</span><span class="pln"> result </span><span class="pun">=</span><span class="pln"> apiInstance</span><span class="pun">.</span><span class="pln">showBuildings</span><span class="pun">();</span><span class="pln">
                </span><span class="typ">Debug</span><span class="pun">.</span><span class="typ">WriteLine</span><span class="pun">(</span><span class="pln">result</span><span class="pun">);</span><span class="pln">
            </span><span class="pun">}</span><span class="pln">
            </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">Exception</span><span class="pln"> e</span><span class="pun">)</span><span class="pln">
            </span><span class="pun">{</span><span class="pln">
                </span><span class="typ">Debug</span><span class="pun">.</span><span class="typ">Print</span><span class="pun">(</span><span class="str">"Exception when calling BuildingsApi.showBuildings: "</span><span class="pln"> </span><span class="pun">+</span><span class="pln"> e</span><span class="pun">.</span><span class="typ">Message</span><span class="pln"> </span><span class="pun">);</span><span class="pln">
            </span><span class="pun">}</span><span class="pln">
        </span><span class="pun">}</span><span class="pln">
    </span><span class="pun">}</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Buildings-showBuildings-0-php">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-php"><span class="pun">&lt;?</span><span class="pln">php
require_once</span><span class="pun">(</span><span class="pln">__DIR__ </span><span class="pun">.</span><span class="pln"> </span><span class="str">'/vendor/autoload.php'</span><span class="pun">);</span><span class="pln">

$api_instance </span><span class="pun">=</span><span class="pln"> </span><span class="kwd">new</span><span class="pln"> </span><span class="typ">Swagger</span><span class="pln">\Client\ApiBuildingsApi</span><span class="pun">();</span><span class="pln">

</span><span class="kwd">try</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    $result </span><span class="pun">=</span><span class="pln"> $api_instance</span><span class="pun">-&gt;</span><span class="pln">showBuildings</span><span class="pun">();</span><span class="pln">
    print_r</span><span class="pun">(</span><span class="pln">$result</span><span class="pun">);</span><span class="pln">
</span><span class="pun">}</span><span class="pln"> </span><span class="kwd">catch</span><span class="pln"> </span><span class="pun">(</span><span class="typ">Exception</span><span class="pln"> $e</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    echo </span><span class="str">'Exception when calling BuildingsApi-&gt;showBuildings: '</span><span class="pun">,</span><span class="pln"> $e</span><span class="pun">-&gt;</span><span class="pln">getMessage</span><span class="pun">(),</span><span class="pln"> PHP_EOL</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span><span class="pln">
</span><span class="pun">?&gt;</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Buildings-showBuildings-0-perl">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-perl"><span class="kwd">use</span><span class="pln"> </span><span class="typ">Data</span><span class="pun">::</span><span class="typ">Dumper</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">use</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">Configuration</span><span class="pun">;</span><span class="pln">
</span><span class="kwd">use</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">BuildingsApi</span><span class="pun">;</span><span class="pln">

</span><span class="kwd">my</span><span class="pln"> $api_instance </span><span class="pun">=</span><span class="pln"> WWW</span><span class="pun">::</span><span class="typ">SwaggerClient</span><span class="pun">::</span><span class="typ">BuildingsApi</span><span class="pun">-&gt;</span><span class="pln">new</span><span class="pun">();</span><span class="pln">

</span><span class="kwd">eval</span><span class="pln"> </span><span class="pun">{</span><span class="pln"> 
    </span><span class="kwd">my</span><span class="pln"> $result </span><span class="pun">=</span><span class="pln"> $api_instance</span><span class="pun">-&gt;</span><span class="pln">showBuildings</span><span class="pun">();</span><span class="pln">
    </span><span class="kwd">print</span><span class="pln"> </span><span class="typ">Dumper</span><span class="pun">(</span><span class="pln">$result</span><span class="pun">);</span><span class="pln">
</span><span class="pun">};</span><span class="pln">
</span><span class="kwd">if</span><span class="pln"> </span><span class="pun">(</span><span class="pln">$@</span><span class="pun">)</span><span class="pln"> </span><span class="pun">{</span><span class="pln">
    warn </span><span class="str">"Exception when calling BuildingsApi-&gt;showBuildings: $@\n"</span><span class="pun">;</span><span class="pln">
</span><span class="pun">}</span></code></pre>
                            </div>

                            <div class="tab-pane" id="examples-Buildings-showBuildings-0-python">
                              <pre class="prettyprint prettyprinted" style=""><code class="language-python"><span class="kwd">from</span><span class="pln"> __future__ </span><span class="kwd">import</span><span class="pln"> print_statement
</span><span class="kwd">import</span><span class="pln"> time
</span><span class="kwd">import</span><span class="pln"> swagger_client
</span><span class="kwd">from</span><span class="pln"> swagger_client</span><span class="pun">.</span><span class="pln">rest </span><span class="kwd">import</span><span class="pln"> </span><span class="typ">ApiException</span><span class="pln">
</span><span class="kwd">from</span><span class="pln"> pprint </span><span class="kwd">import</span><span class="pln"> pprint

</span><span class="com"># create an instance of the API class</span><span class="pln">
api_instance </span><span class="pun">=</span><span class="pln"> swagger_client</span><span class="pun">.</span><span class="typ">BuildingsApi</span><span class="pun">()</span><span class="pln">

</span><span class="kwd">try</span><span class="pun">:</span><span class="pln"> 
    </span><span class="com"># Show a list of buildings with available floors</span><span class="pln">
    api_response </span><span class="pun">=</span><span class="pln"> api_instance</span><span class="pun">.</span><span class="pln">show_buildings</span><span class="pun">()</span><span class="pln">
    pprint</span><span class="pun">(</span><span class="pln">api_response</span><span class="pun">)</span><span class="pln">
</span><span class="kwd">except</span><span class="pln"> </span><span class="typ">ApiException</span><span class="pln"> </span><span class="kwd">as</span><span class="pln"> e</span><span class="pun">:</span><span class="pln">
    </span><span class="kwd">print</span><span class="pun">(</span><span class="str">"Exception when calling BuildingsApi-&gt;showBuildings: %s\n"</span><span class="pln"> </span><span class="pun">%</span><span class="pln"> e</span><span class="pun">)</span></code></pre>
                            </div>
                          </div>

                          <h2>Parameters</h2>






                          <h2>Responses</h2>
                            <h3> Status: 200 - successful operation </h3>

                            <ul class="nav nav-tabs nav-tabs-examples">
                                <li class="active">
                                  <a data-toggle="tab" href="#responses-showBuildings-200-schema">Schema</a>
                                </li>

                            </ul>

                            <div class="tab-content" style="margin-bottom: 10px;">
                                <div class="tab-pane active" id="responses-showBuildings-200-schema">
                                  <div id="responses-showBuildings-200-schema-200" style="padding: 30px; border-left: 1px solid #eee; border-right: 1px solid #eee; border-bottom: 1px solid #eee;"><div class="json-schema-view"><div class="array">
          <a class="title"><span class="toggle-handle"></span><span class="opening bracket">[</span></a>
          <div class="inner">
          <div class="json-schema-view"><div class="primitive">
            <span class="type">undefined</span>
        </div></div></div>
          <span class="closing bracket">]</span>
        </div></div></div>
                                  <input id="responses-showBuildings-200-schema-data" type="hidden" value="{&quot;type&quot;:&quot;array&quot;,&quot;items&quot;:{&quot;$ref&quot;:&quot;#/components/schemas/Buildings&quot;},&quot;x-content-type&quot;:&quot;application/json&quot;}">
                                </div>
                            </div>

                        </article>
                      </div>
                      <hr>
                  </section>
          </div>
          <div id="footer">
            <div id="api-_footer">
              <p>Suggestions, contact, support and error reporting;
                  </p><div class="app-desc">Information URL: <a href="https://helloreverb.com">https://helloreverb.com</a></div>
                  <div class="app-desc">Contact Info: <a href="pankratiew@brakhmen.info">pankratiew@brakhmen.info</a></div>
              <p></p>
                <div class="license-info">Apache 2.0</div>
                <div class="license-url">http://www.apache.org/licenses/LICENSE-2.0.html</div>
            </div>
          </div>
      </div>
