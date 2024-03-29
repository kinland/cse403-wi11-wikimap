<?php
	// This API is used by the front-end to store image URLs and preview texts of articles that
	// are retrieved later. It is also used by the database retriever to cache relevancy trees and
	// for removing expired relevancy trees.
	include("cacher.php");
	$db_cache = new DatabaseCacher;
	$debug = false;

	if($debug) {
			$function = $_GET['function'];
			$article = $_GET['article'];
			$maxDepth = $_GET['maxDepth'];
			$numNodes = $_GET['numNodes'];
			$data = $_GET['data'];
	} else {
			$function = $_POST['function'];
			$article = $_POST['article'];
			$maxDepth = $_POST['maxDepth'];
			$numNodes = $_POST['numNodes'];
			$data = $_POST['data'];
	}

	// next line is a temporary hack just for the alpha
	//$foundarticle = $db_ret->getPreviewText($article) != null
	if ($function == 'insertImageURL') {
			$db_cache->insertImageURL($article, $data);
	}
	else if ($function == 'insertPreviewText') {
			$db_cache->insertPreviewText($article, $data);
	}
	else if ($function == 'insertTree'){
			$db_cache->insertTree($article, $maxDepth, $numNodes, $data);
	}
	else if ($function == 'refreshCache'){
			$db_cache->refreshCache();
	}

?>
