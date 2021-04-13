#!/bin/bash
echo Updating from git ...
git pull
echo Building Test01 ...
cd Self\JDK_Ant_Eclipse\App\Dust\Test01
ant
cd ..\..\..\..\..