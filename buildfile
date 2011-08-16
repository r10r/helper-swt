repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.release_to = 'file:///home/ruben/workspace/rjenster-mvn-repo/releases'

require 'time'

THIS_VERSION = '0.1'

ant_layout = Layout.new

ant_layout[:source, :main, :java] = 'src'
ant_layout[:source, :main, :resources] = 'resource'
ant_layout[:source, :test, :java] = 'test'

desc 'swt-wrapper'
define 'swt-wrapper', :layout=>ant_layout do
  project.group = 'eu.jenster'
  project.version = THIS_VERSION
  package :sources
  package :javadoc
  package(:jar).with :manifest=>
  { 
    'Project' => project.id,
    'Copyright' => 'Ruben Jenster (C) 2011',
    'Version' => THIS_VERSION,
    'Creation' => Time.now.strftime("%a, %d %b %Y %H:%M:%S %z")
  }

end
