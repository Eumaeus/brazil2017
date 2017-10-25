
// must be at least 2.11 to use hmt_textmodel
scalaVersion := "2.12.3"

resolvers += Resolver.jcenterRepo
resolvers += "beta" at "http://beta.hpcc.uh.edu/nexus/content/repositories/releases"
resolvers += Resolver.bintrayRepo("neelsmith", "maven")
libraryDependencies ++=   Seq(
  "org.scalatest" % "scalatest_2.12" % "3.0.1" % "test",
  "edu.holycross.shot" %% "seqcomp" % "1.0.0",
  "edu.holycross.shot.cite" %% "xcite" % "3.2.1",
  "edu.holycross.shot" %% "ohco2" % "10.4.0",
  "edu.holycross.shot" %% "scm" % "5.1.6"
)
