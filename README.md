## performance notes
### follow these best practices:
All to-many associations use FetchType.LAZY by default, and you should not change that.
All to-one associations use FetchType.EAGER by default, and you should set it to LAZY. 
You can do that by setting the fetch attribute on the @ManyToOne or @OneToOne annotation.

@ManyToOne(fetch=FetchType.LAZY)

