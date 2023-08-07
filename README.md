## performance notes
### follow these best practices:
All to-many associations use FetchType.LAZY by default, and you should not change that.
All to-one associations use FetchType.EAGER by default, and you should set it to LAZY. 
You can do that by setting the fetch attribute on the @ManyToOne or @OneToOne annotation.

@ManyToOne(fetch=FetchType.LAZY)

### TODO
- 1 create data structure
  - tables
  - populate with data
- 2 create scenarios with FetchTypes
  - generate metrics
- 3 create scenarios with Indexes
  - generate metrics
- 4 create scenarios with Caches
  - enable cache level 1
  - enable cache level 2
  - generate metrics
- 5 create scenarios using batch process
  - generate metrics
- 6 generate reports
  - done