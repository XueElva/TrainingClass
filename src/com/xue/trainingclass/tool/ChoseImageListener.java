/**
 *
 */
package com.xue.trainingclass.tool;


import com.xue.trainingclass.bean.ImageBean;

/**
 * @author xiaolf1
 */
public interface ChoseImageListener {

    public boolean onSelected(ImageBean image);

    public boolean onCancelSelect(ImageBean image);
}
