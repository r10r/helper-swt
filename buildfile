repositories.remote << 'http://www.ibiblio.org/maven2'
repositories.release_to = File.read(File.join(ENV['HOME'],'.buildr','repository-release')).strip

require 'time'

THIS_VERSION = '0.1'

ant_layout = Layout.new

ant_layout[:source, :main, :java] = 'src'
ant_layout[:source, :main, :resources] = 'resource'
ant_layout[:source, :test, :java] = 'test'


SWT_ARCHS = {
  :linux => {
    :x86 => "gtk-linux-x86",
    :x86_64 => "gtk-linux-x86_64"
    },
  :windows => {
    :x86 => "win32-win32-x86",
    :x86_64 => "win32-win32-x86_64"
    },
  :mac => {
    :x86 => "cocoa-macosx",
    :x86_64 => "cocoa-macosx-x86_64"
  }
}

SWT_VERSION = '3.7'
SWT_DROP = "R-#{SWT_VERSION}-201106131736"
SWT_ARCH = SWT_ARCHS[:linux][:x86_64]
SWT_DEPENDENCY = "org.eclipse.swt:swt-#{SWT_ARCH}:jar:#{SWT_VERSION}"

desc 'a host of helper utilities for the Standard Widget Tookit (SWT)'

define 'helper-swt', :layout=>ant_layout do
  project.group = 'eu.jenster.helper'
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

	def swt_download(swt_arch)
    # http://buildr.apache.org/artifacts.html#install_upload
    # file task for downloading swt  
    swt_filename = "swt-#{SWT_VERSION}-#{swt_arch}"
    url="http://ftp-stud.fht-esslingen.de/pub/Mirrors/eclipse/eclipse/downloads/drops/#{SWT_DROP}/#{swt_filename}.zip"
    zip = download("target/#{swt_filename}.zip"=>url)
    jar = file("target/#{swt_filename}/swt.jar"=>unzip("target/#{swt_filename}"=>zip))
    spec = "org.eclipse.swt:swt-#{swt_arch}:jar:#{SWT_VERSION}"
    artifact = artifact(spec).from(jar)
    
    if not artifact.exist?      
      puts "Downloading SWT for arch[#{swt_arch}]"
      artifact.install
    end
    # clear the artifact, to prevent it to be downloaded again
    artifact.clear
  end
  
  swt_download(SWT_ARCH)
  
   # add the SWT development library to the compile time dependencies
  compile.with(
  	SWT_DEPENDENCY
  )

end
