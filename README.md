# MyBatis Profiler

MyBatisProfiler is a Plugin for MyBatis that keeps track of executed mapped 
statements.

Example output:

```
/app/secure/home/index.jsf
+------------------------------------------+-------+---------------+---------------+
| MappedStatement                          | Times | Min. Response | Max. Response |
+-------------------- ---------------------+-------+---------------+---------------+
| org.raupach.data.FooMapper.selectByValue |      7|              1|              4|
| org.raupach.data.FooMapper.selectOne     |      1|              6|              6|
+------------------------------------------+-------+---------------+---------------+
```


## Installation

First add `mybatis-profiler-0.1.jar` to your classpath.

Next register the plugin in your MyBatis configuration. You can do this either
in the XML configuration file or programmatically.


```
<plugins>
  <plugin interceptor="org.raupach.plugin.profiler.MyBatisProfiler" />
</plugins>
```

```
configuration.addInterceptor(new MybatisProfiler());
```

## Usage

You can start and stop profiling programmatically or with a servlet filter.

### Servlet Filter

Configure the MyBatisProfilerFilter in your web.xml

```
<filter>
  <filter-name>MybatisProfilerFilter</filter-name>
  <filter-class>org.raupach.plugin.profiler.MybatisProfilerFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>MybatisProfilerFilter</filter-name>
  <servlet-name>Faces Servlet</servlet-name>
</filter-mapping>
```

### Programmatically

```
Session session = SessionManager.newSession("Untitled0");
[..]
SessionManager.destroySession();
```



