package com.jackamikaz.gameengine.saveload;

import java.io.IOException;

public interface SaverLoaderObject {
	void handleSaveLoad(SaverLoader sl) throws IOException;
}
