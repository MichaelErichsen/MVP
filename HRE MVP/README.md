This project is an attempt to sketch a minimum viable product using the philosphy of the HRE data model in an extremely reduced form.
It is intended to grow into an HRE implementation, if possible.

Eclipse Photon Bug:

When trying to open the SQL Results View I get the following exceptions.  I've reinstalled eclipse multiple times with same results.  Eclipse is installed with 3 different versions of the Lucene core jar - 3.5.0, 6.1.0 and 7.1.0.  The stack trace seems to indicate that Eclipse is trying to access a method in the 7.1.0 jar, but can't find it, probably because it's picking up methods from the other two versions.

org.eclipse.core.runtime.CoreException: Plug-in org.eclipse.datatools.sqltools.result.ui was unable to load class org.eclipse.datatools.sqltools.result.internal.ui.view.ResultsView.

Work-around: 

Following advise was helpful for me, so no re-installation was required. May it be changes in format of result history during Eclipse upgrade?

Go to work space directory > Open ".metadata" folder > Open ".plugins" folder > Open "org.eclipse.datatools.sqltools.result" folder.
Delete the "results" file.
Re-start the product and try again.
