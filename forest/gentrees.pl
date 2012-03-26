#!/usr/bin/perl
#
# Usage: gentrees.pl <output file> <number of trees> <half of ground width>
#

$filename = $ARGV[0];
$num_trees = $ARGV[1];
$width = $ARGV[2];

open(FILE, ">$filename") or die "Cannot open $filename\n";

for($i=0; $i < $num_trees; $i++) {
	print FILE (rand($width * 20)/10.0 - $width) . ",";
	print FILE (rand(100)/10.0 + 2.0) . ",";
	print FILE (rand($width * 20)/10.0 - $width) . ",";
	print FILE (rand(60)/100.0 + 0.1);
	print FILE "\n";
}

close(FILE);
