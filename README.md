# dynamic-scheduling-postgres
Supports jdbc store and clustering...
To remove clustering remove below props from yml file
org.quartz.jobStore.isClustered: true
org.quartz.jobStore.clusterCheckinInterval: 20000

To disable jdbc store comment all the quartz props from yml and remove bean of SpringQrtzScheduler.